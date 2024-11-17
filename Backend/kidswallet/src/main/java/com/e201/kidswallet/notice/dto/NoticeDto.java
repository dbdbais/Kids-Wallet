package com.e201.kidswallet.notice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoticeDto implements Serializable {
    //아이가 부모님께 용돈을 요청(~~님이 용돈을 요청했어요!)
    //아이가 미션을 수행(~~님이 미션을 수행했어요!)
    //아이가 같이달리기를 요청(~~님이 같이달리기를 요청했어요!)
    //부모님이 용돈을 주심(~~님이 용돈을 주셨어요!)
    //부모님이 미션을 주심(~~님이 미션을 주셨어요!)
    //부모님이 아이를 추가함(~~님이 ~~님을 추가하셨어요!)
    //부모님이 조르기를 거절함(~~님이 조르기를 거절했어요!)=
    //부모님이 미션을 거절함(~~님이 미션을 거절했어요!)
    //부모님이 같이달리기를 거절함(~~님이 같이달리기를 거절했어요!){
    private String message;
}
