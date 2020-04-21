package com.example.homework_4_fragments

data class RecyclerMoviesItem(
    var image: Int,
    var title: String,
    var description: String,
    var id: Int
) {

    fun setImage() {
        this.image = image
    }

    fun setTitle() {
        this.title = title
    }

    fun setDescription() {
        this.description = description
    }

    fun setId() {
        this.id = id
    }


    override fun toString(): String {
        return ("Movie [id=" + id + ", title=" + title + ", description="
                + description + ", image=" + image + "]")
    }
}

val image: Int
    get() {
        return image
    }

val title: String
    get() {
        return title
    }

val description: String
    get() {
        return description
    }

val id: Int
    get() {
        return id
    }