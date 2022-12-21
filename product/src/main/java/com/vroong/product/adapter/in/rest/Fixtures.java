package com.vroong.product.adapter.in.rest;

import com.vroong.product.domain.Product;
import com.vroong.product.domain.Size;
import com.vroong.product.rest.PageDto;
import com.vroong.product.rest.ProductDto;
import com.vroong.product.rest.ProductListDto;
import com.vroong.product.rest.SizeDto;
import com.vroong.shared.Money;
import java.math.BigDecimal;

public class Fixtures {

    static final Long DEFAULT_ID = 1L;
    static final String DEFAULT_PRODUCT_NAME = "카이엔";
    static final String DEFAULT_PRODUCT_DESCRIPTION = "카이엔 라인업에 새롭게 선보이는 카이엔 터보 GT는 강렬한 퍼포먼스와 정밀한 다이내믹스로 레이스 트랙의 스타트 라인에 자리를 차지했습니다. 카이엔 터보 GT의 모든 특성은 시작부터 레이스를 앞서갈 수 있도록 초점이 맞춰져 있습니다.";
    static final String DEFAULT_PRODUCT_SUPPLIER = "포르쉐";
    static final String DEFAULT_PRODUCT_LOCATION = "선릉 전시장";

    public static Product aProduct() {
        return new Product(
            DEFAULT_PRODUCT_NAME,
            DEFAULT_PRODUCT_DESCRIPTION,
            new Money(new BigDecimal(500_000_000)),
            1,
            DEFAULT_PRODUCT_SUPPLIER,
            new Size(4846, 1939, 1705),
            DEFAULT_PRODUCT_LOCATION
        );
    }

    public static ProductDto aProductDto() {
        return new ProductDto()
                .name(DEFAULT_PRODUCT_NAME)
                .description(DEFAULT_PRODUCT_DESCRIPTION)
                .price(new BigDecimal(500_000_000))
                .inventory(1)
                .supplier(DEFAULT_PRODUCT_SUPPLIER)
                .size(new SizeDto().width(4846).height(1939).depth(1705))
                .location(DEFAULT_PRODUCT_LOCATION);
    }

    public static ProductListDto aProductListDto() {
        return new ProductListDto()
            .addDataItem(aProductDto())
            .page(new PageDto()
                .number(1)
                .size(10)
                .totalElements(1L)
                .totalPages(1));
    }
}
