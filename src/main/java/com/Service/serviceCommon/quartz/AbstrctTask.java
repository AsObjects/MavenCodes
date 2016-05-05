package com.Service.serviceCommon.quartz;

import javax.annotation.Resource;

import org.quartz.Job;
/*
 * 实现了job接口，每一个任务的job都必须继承于这个类
 */
public abstract class AbstrctTask implements Job{

    protected String fqn;
    
    // 刷新时间，分钟为单位
    protected  int freshTime ;
    
    @Resource
    protected IQuartz quartz;
    
    
    public AbstrctTask() {
    }

    
    public String getFqn() {
        return this.fqn;
    }

    public void setFqn(String fqn) {
        this.fqn = fqn;
    }
    
    public int getFreshTime() {
        if(freshTime==0) {
            freshTime=5;
        }
        return this.freshTime;
    }

    public void setFreshTime(int freshTime) {
        this.freshTime = freshTime;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result =
                prime * result + ((this.fqn == null) ? 0 : this.fqn.hashCode());
        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final AbstrctTask other = (AbstrctTask) obj;
        if (this.fqn == null) {
            if (other.fqn != null)
                return false;
        } else if (!this.fqn.equals(other.fqn))
            return false;
        return true;
    }
}
