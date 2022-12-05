Order
1. 주문한다.
    * request: 주문 상품 목록
    * response: 배송비, 총 결제 금액, 주문 상품 목록
    * Placed가 되고, 응답
2. 결제한다.
    * request: Sender, Receiver, OrderId, 카드번호
    * response: 성공 / 실패
    * 동기로 payment, delivery에 확인 -> CANCELED or PAYMENT_APPROVED
    * 실패
        * payment가 안 될 경우 -> 결제 실패 응답
        * delivery가 안 될 경우 -> payment를 취소 (비동기)
3. 주문을 확정한다.
    * 배송이 완료되었을 때 주문 상태를 변경하지 못하도록
    * request: orderId
    * response:
    * DELIVERY_COMPLETED 상태로 변경
4. 주문을 수정한다.
    * request: orderId, 주문 상품 목록, Sender, Receiver, 카드번호
    * response: 성공 / 실패
    * 동기로 payment, delivery에 확인 -> CANCELED or PAYMENT_APPROVED
    * 성공
        * 결제 전, 배송 전
    * 실패
        * 배송 시작 이후
        * payment가 안 될 경우 -> 결제 실패 응답 -> CANCELED
        * delivery가 안 될 경우 -> payment를 취소 (비동기) -> CANCELED
5. 주문을 취소한다.
    * request: orderId
    * response: 성공 / 실패
    * 성공
        * 결제 전, 배송 전
    * 실패
        * 배송 시작 이후
    * CANCELED 상태로 변경
6. 주문을 조회한다.
    * request: orderId
    * response: orderId, 주문 상품 목록, Sender, Receiver, 카드번호, 총 금액