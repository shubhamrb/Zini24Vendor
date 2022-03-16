package com.mamits.zini24vendor.ui.utils.rx;

import io.reactivex.Scheduler;

public interface ISchedulerProvider {
    Scheduler io();

    Scheduler ui();

}
