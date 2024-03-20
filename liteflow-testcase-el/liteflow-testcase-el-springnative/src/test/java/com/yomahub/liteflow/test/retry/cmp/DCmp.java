package com.yomahub.liteflow.test.retry.cmp;

import com.yomahub.liteflow.core.NodeSwitchComponent;
import org.springframework.stereotype.Component;

@Component("d")
public class DCmp extends NodeSwitchComponent {
    int flag = 0;

    @Override
    public String processSwitch() throws Exception {
        flag ++;
        System.out.println("DCmp executed!");
        if(flag < 4) throw new RuntimeException();
        else return "a";
    }
}
