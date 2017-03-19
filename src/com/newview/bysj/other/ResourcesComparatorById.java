package com.newview.bysj.other;

import java.util.Comparator;

import com.newview.bysj.domain.Resource;

/**
 * 对资源根据id来比较
 */
public class ResourcesComparatorById implements Comparator<Resource> {

    /**
     * 对资源进行比较
     *
     * @param resource1 资源1
     * @param resource2 资源2
     * @return 如果相等则返回0，如果resource1的id大于resource2的id则返回一个大于0的数，否则返回一个小于0的数
     */
    @Override
    public int compare(Resource resource1, Resource resource2) {
        return resource1.getId().compareTo(resource2.getId());
    }

}
