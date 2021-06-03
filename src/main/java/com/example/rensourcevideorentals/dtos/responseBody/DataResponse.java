package com.example.rensourcevideorentals.dtos.responseBody;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
public class DataResponse<M> {

    private boolean valid;

    private M data;

    private List<Message> messages;

    public DataResponse(boolean valid, String message) {
        this.valid = valid;
        this.addMessage(message);
    }

    public DataResponse(boolean valid, M data) {
        this.valid = valid;
        this.data = data;
        this.addMessage("Request was successful");
    }

    public DataResponse(boolean valid, M data,String message) {
        this.valid = valid;
        this.data = data;
        this.addMessage(message);
    }


    public final void addMessage(Message msg) {
        if (msg != null) {
            if (this.messages == null) {
                this.messages = new ArrayList();
            }
            this.messages.add(msg);
        }
    }


    public final void addMessage(String msg) {
        addMessage(msg, null);
    }


    public final void addMessage(String msg, Message.Severity severity) {
        addMessage(msg, null, severity);
    }


    public final void addMessage(String msg, String detail, Message.Severity severity) {
        if (msg != null || detail != null) {
            addMessage(new MessageTemplate(msg, detail, severity));
        }
    }

    public boolean hasMessages() {
        return this.messages != null && !this.messages.isEmpty();
    }


}
