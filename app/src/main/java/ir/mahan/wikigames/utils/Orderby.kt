package ir.mahan.wikigames.utils

enum class Orderby(val order: String) {
    NAMEASCENDING("name"),
    NAMEDESCENDING("-name"),
    RELEASEDASCENDING("released"),
    RELEASEDDESCENDING("-released"),
    ADDEDASCENDING("added"),
    ADDEDDESCENDING("-added"),
    CREATEDASCENDING("created"),
    CREATEDDESCENDING("-created"),
    UPDATEDASCENDING("updated"),
    UPDATEDDESCENDING("-updated"),
    RATINGASCENDING("rating"),
    RATINGDESCENDING("-rating"),
    METACRITICASCENDING("metacritic"),
    METACRITICDESCENDING("-metacritic")
}