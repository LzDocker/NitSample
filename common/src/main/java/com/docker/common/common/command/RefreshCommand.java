package com.docker.common.common.command;

import com.docker.common.common.widget.empty.EmptyCommand;

public class RefreshCommand {

    public EmptyCommand retrycommand;

    public ReplyCommand onrefreshCommand;

    public ReplyCommand onloadmoreCommand;

    public void OnRetryLoad(EmptyCommand retrycommand) {
        this.retrycommand = retrycommand;
    }

    public void OnRefresh(ReplyCommand onrefreshCommand) {
        this.onrefreshCommand = onrefreshCommand;
    }

    public void OnLoadMore(ReplyCommand onloadmoreCommand) {

        this.onloadmoreCommand = onloadmoreCommand;

    }
}
