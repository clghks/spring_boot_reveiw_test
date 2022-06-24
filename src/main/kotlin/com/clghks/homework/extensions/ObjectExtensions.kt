package com.clghks.homework.extensions

import org.springframework.beans.BeanWrapperImpl

fun <T : Any> T.getNullPropertyNames(): Array<String> {
    val src = BeanWrapperImpl(this)
    val emptyNames = HashSet<String>()
    for (item in src.propertyDescriptors) {
        if (src.getPropertyValue(item.name) == null) {
            emptyNames.add(item.name)
        }
    }
    return emptyNames.toTypedArray()
}