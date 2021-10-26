package com.coldkitchen.severalminuteworkout

class ExerciseModel(private var id: Int,
                    private var name: String,
                    private var image: Array<Int>,
                    private var isComplited: Boolean,
                    private var isSelected: Boolean) {

    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getName(): String {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }

    fun getImagesArray(): Array<Int> {
        return image
    }

    fun setImage(image: Int) {
        //this.image = image
    }

    fun getIsComplited(): Boolean {
        return isComplited
    }

    fun setIsComplited(isComplited: Boolean) {
        this.isComplited = isComplited
    }

    fun getIsSelected(): Boolean {
        return isSelected
    }

    fun setIsSelected(isSelected: Boolean) {
        this.isSelected = isSelected
    }
}