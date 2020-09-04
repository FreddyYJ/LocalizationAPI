package com.github.freddyyj.localizationtest;

import com.github.freddyyj.localizationapi.Core;
import com.github.freddyyj.localizationapi.Language;
import org.junit.Test;

public class MainTest {
    @Test
    public void FileReadTest(){
        Core core=new Core();
        Language korean=core.getLanguage("ko-kr");
        System.out.println(korean.getText("message.welcome"));
        Language english=core.getLanguage("en-us");
        System.out.println(english.getText("message.welcome"));

        Language uk=core.getLanguage("en-uk");
        System.out.println(uk.getText("message.welcome"));

    }
}
