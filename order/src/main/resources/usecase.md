Order
1. 주문한다.
    * request: 주문 상품 목록, Sender, Receiver
    * response: 배송비, 총 결제 금액, 주문 상품 목록
    * 성공
      * Placed 상태로 등록
      * 이벤트
         * ORDER_CREATED
2. 주문을 수정한다.
    * request: orderId, 주문 상품 목록, Sender, Receiver
    * response: 성공 / 실패
    * 성공
        * 결제 전, 배송 전
        * 이벤트
          * ORDER_UPDATED 
    * 실패
        * 배송 시작 이후
        * payment가 안 될 경우 -> 결제 실패 응답 -> CANCELED
        * delivery가 안 될 경우 -> payment를 취소 (비동기) -> CANCELED
3. 주문을 취소한다.
    * request: orderId
    * response: 성공 / 실패
    * 성공
        * 결제 전, 배송 전
        * CANCELED 상태로 변경
        * 이벤트
           * ORDER_CANCELED
    * 실패
        * 배송 시작 이후
4. 개별 주문을 조회한다.
    * request: orderId
    * response: Order
5. 주문 목록을 조회한다.
    * request: -
    * response: Order 목록
