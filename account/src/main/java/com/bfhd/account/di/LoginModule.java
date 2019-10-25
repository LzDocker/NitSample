package com.bfhd.account.di;

import com.docker.common.common.vo.UserInfoVo;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginModule {

    @Provides
    UserInfoVo provideUserInfoVo() {
        UserInfoVo userInfoVo = new UserInfoVo();
        userInfoVo.nickname = "provide_by_login_module";
        return userInfoVo;
    }

}
