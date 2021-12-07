package ru.mrroot.mytets2.view.book

import ru.mrroot.mytets2.view.ViewContract

interface ViewDetailsContract : ViewContract {
    fun setCount(count: Int)
}