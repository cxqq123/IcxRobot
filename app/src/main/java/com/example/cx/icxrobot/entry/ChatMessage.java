package com.example.cx.icxrobot.entry;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by cx
 * on 2018/8/28
 */

@Entity
public class ChatMessage {

    @Id(autoincrement = true)
    private Long id;
    private String content;
    private String date;
    private String icon;
    private String username;
    private String group;
    private boolean isMyInfo;
    private String tag;
    @Generated(hash = 1511994733)
    public ChatMessage(Long id, String content, String date, String icon,
            String username, String group, boolean isMyInfo, String tag) {
        this.id = id;
        this.content = content;
        this.date = date;
        this.icon = icon;
        this.username = username;
        this.group = group;
        this.isMyInfo = isMyInfo;
        this.tag = tag;
    }
    @Generated(hash = 2271208)
    public ChatMessage() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getDate() {
        return this.date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getIcon() {
        return this.icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getGroup() {
        return this.group;
    }
    public void setGroup(String group) {
        this.group = group;
    }
    public boolean getIsMyInfo() {
        return this.isMyInfo;
    }
    public void setIsMyInfo(boolean isMyInfo) {
        this.isMyInfo = isMyInfo;
    }
    public String getTag() {
        return this.tag;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }
}
