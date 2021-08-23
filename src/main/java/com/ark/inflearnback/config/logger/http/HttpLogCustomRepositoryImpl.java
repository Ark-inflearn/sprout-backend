package com.ark.inflearnback.config.logger.http;

import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.StringTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.ark.inflearnback.config.logger.http.QHttpLog.httpLog;
import static com.querydsl.core.types.dsl.Expressions.stringTemplate;

@RequiredArgsConstructor
public class HttpLogCustomRepositoryImpl implements HttpLogCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    @Transactional(readOnly = true)
    public Long searchDau() {
        return queryFactory
                .select(httpLog.clientIp.countDistinct())
                .from(httpLog)
                .where(httpLog.regDate.gt(onTime()))
                .fetchOne();

    }

    @Override
    @Transactional(readOnly = true)
    public Long searchTotalVisitors() {
        return queryFactory
                .select(httpLog.clientIp.countDistinct())
                .from(httpLog)
                .groupBy(date(httpLog.regDate))
                .fetch()
                .stream()
                .reduce(0L, Long::sum);
    }

    private StringTemplate date(DateTimePath regDate) {
        return stringTemplate("date({0})", regDate);
    }

    private LocalDateTime onTime() {
        return LocalDateTime.of(LocalDateTime.now().getYear(),
                LocalDateTime.now().getMonth(),
                LocalDateTime.now().getDayOfMonth(),
                0, 0, 0);
    }
}
