package ru.lissdev.sportnote.databinding

import androidx.databinding.ObservableField

class ObservableLong(default: Long = 0L): ObservableField<Long>() {

    var value = default.also { set(it) }

    override fun set(value: Long?) {
        if (value != this.value) {
            super.set(value)
        }
    }
}
