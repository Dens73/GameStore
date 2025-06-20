package com.gamestore.app.data

import com.gamestore.app.models.Game
import com.gamestore.app.models.GameCategory
import com.gamestore.app.models.Platform

object GameRepository {
    
    fun getAllGames(): List<Game> {
        return listOf(
            Game(
                id = 1,
                title = "Cyberpunk 2077",
                description = "Откройте для себя мир будущего в этой захватывающей RPG с открытым миром",
                price = 2999.0,
                originalPrice = 3999.0,
                image = "https://upload.wikimedia.org/wikipedia/ru/b/bb/%D0%9E%D0%B1%D0%BB%D0%BE%D0%B6%D0%BA%D0%B0_%D0%BA%D0%BE%D0%BC%D0%BF%D1%8C%D1%8E%D1%82%D0%B5%D1%80%D0%BD%D0%BE%D0%B9_%D0%B8%D0%B3%D1%80%D1%8B_Cyberpunk_2077.jpg",
                category = GameCategory.RPG,
                genre = "Action RPG",
                rating = 4.2f,
                platform = listOf(Platform.PC, Platform.PLAYSTATION, Platform.XBOX),
                releaseDate = "2020",
                developer = "CD Projekt RED",
                isOnSale = true,
                discountPercent = 25
            ),
            Game(
                id = 2,
                title = "The Witcher 3",
                description = "Эпическое фэнтези приключение в роли ведьмака Геральта",
                price = 1499.0,
                image = "https://upload.wikimedia.org/wikipedia/ru/thumb/a/a2/The_Witcher_3-_Wild_Hunt_Cover.jpg/330px-The_Witcher_3-_Wild_Hunt_Cover.jpg",
                category = GameCategory.RPG,
                genre = "Action RPG",
                rating = 4.8f,
                platform = listOf(Platform.PC, Platform.PLAYSTATION, Platform.XBOX, Platform.NINTENDO_SWITCH),
                releaseDate = "2015",
                developer = "CD Projekt RED"
            ),
            Game(
                id = 3,
                title = "Call of Duty: MW3",
                description = "Новейший шутер от первого лица с захватывающим мультиплеером",
                price = 4999.0,
                image = "https://upload.wikimedia.org/wikipedia/ru/thumb/0/0b/Call_Of_Duty_MW3_2023.jpg/330px-Call_Of_Duty_MW3_2023.jpg",
                category = GameCategory.NEW_RELEASES,
                genre = "FPS",
                rating = 4.5f,
                platform = listOf(Platform.PC, Platform.PLAYSTATION, Platform.XBOX),
                releaseDate = "2023",
                developer = "Infinity Ward"
            ),
            Game(
                id = 4,
                title = "Baldur's Gate 3",
                description = "Классическая RPG с пошаговыми боями и глубоким сюжетом",
                price = 3499.0,
                image = "https://upload.wikimedia.org/wikipedia/ru/d/dc/Baldur%27s_Gate_III_Logo.png",
                category = GameCategory.RPG,
                genre = "Turn-based RPG",
                rating = 4.9f,
                platform = listOf(Platform.PC, Platform.PLAYSTATION),
                releaseDate = "2023",
                developer = "Larian Studios"
            ),
            Game(
                id = 5,
                title = "Hades",
                description = "Инди-игра в жанре roguelike с потрясающим геймплеем",
                price = 999.0,
                originalPrice = 1499.0,
                image = "https://upload.wikimedia.org/wikipedia/ru/thumb/c/cc/Hades_cover_art.jpg/330px-Hades_cover_art.jpg",
                category = GameCategory.INDIE,
                genre = "Roguelike",
                rating = 4.7f,
                platform = listOf(Platform.PC, Platform.PLAYSTATION, Platform.XBOX, Platform.NINTENDO_SWITCH),
                releaseDate = "2020",
                developer = "Supergiant Games",
                isOnSale = true,
                discountPercent = 33
            ),
            Game(
                id = 6,
                title = "Age of Empires IV",
                description = "Стратегическая игра в реальном времени с историческими цивилизациями",
                price = 2799.0,
                image = "https://upload.wikimedia.org/wikipedia/ru/0/08/Age_of_Empires_IV_Cover_Art.png",
                category = GameCategory.STRATEGY,
                genre = "RTS",
                rating = 4.3f,
                platform = listOf(Platform.PC, Platform.XBOX),
                releaseDate = "2021",
                developer = "Relic Entertainment"
            ),
            Game(
                id = 7,
                title = "Spider-Man Remastered",
                description = "Станьте Человеком-пауком в этом захватывающем экшене",
                price = 2299.0,
                originalPrice = 3299.0,
                image = "https://upload.wikimedia.org/wikipedia/ru/d/dd/Spider_Man_PS4_cover.jpg",
                category = GameCategory.ACTION,
                genre = "Action Adventure",
                rating = 4.6f,
                platform = listOf(Platform.PC, Platform.PLAYSTATION),
                releaseDate = "2022",
                developer = "Insomniac Games",
                isOnSale = true,
                discountPercent = 30
            ),
            Game(
                id = 8,
                title = "Hollow Knight",
                description = "Атмосферный метроидвания с прекрасной рисованной графикой",
                price = 699.0,
                image = "https://upload.wikimedia.org/wikipedia/ru/e/eb/Hollow_Knight.jpg",
                category = GameCategory.INDIE,
                genre = "Metroidvania",
                rating = 4.8f,
                platform = listOf(Platform.PC, Platform.PLAYSTATION, Platform.XBOX, Platform.NINTENDO_SWITCH),
                releaseDate = "2017",
                developer = "Team Cherry"
            ),
            Game(
                id = 9,
                title = "Elden Ring",
                description = "Эпическая action-RPG от создателей Dark Souls в открытом мире",
                price = 3999.0,
                image = "https://upload.wikimedia.org/wikipedia/ru/thumb/7/7c/Elden_Ring_-_cover.jpg/330px-Elden_Ring_-_cover.jpg",
                category = GameCategory.NEW_RELEASES,
                genre = "Action RPG",
                rating = 4.9f,
                platform = listOf(Platform.PC, Platform.PLAYSTATION, Platform.XBOX),
                releaseDate = "2022",
                developer = "FromSoftware"
            ),
            Game(
                id = 10,
                title = "God of War",
                description = "Новое приключение Кратоса и Атрея в мире скандинавской мифологии",
                price = 1999.0,
                originalPrice = 2999.0,
                image = "https://upload.wikimedia.org/wikipedia/ru/thumb/5/5a/God_of_War_2018_cover.jpg/330px-God_of_War_2018_cover.jpg",
                category = GameCategory.ACTION,
                genre = "Action Adventure",
                rating = 4.8f,
                platform = listOf(Platform.PC, Platform.PLAYSTATION),
                releaseDate = "2018",
                developer = "Santa Monica Studio",
                isOnSale = true,
                discountPercent = 33
            ),
            Game(
                id = 11,
                title = "Civilization VI",
                description = "Постройте империю, которая выдержит испытание временем",
                price = 1799.0,
                image = "https://upload.wikimedia.org/wikipedia/ru/3/3e/Civilization_VI_Cover_Art.jpg",
                category = GameCategory.STRATEGY,
                genre = "Turn-based Strategy",
                rating = 4.4f,
                platform = listOf(Platform.PC, Platform.PLAYSTATION, Platform.XBOX, Platform.NINTENDO_SWITCH),
                releaseDate = "2016",
                developer = "Firaxis Games"
            ),
            Game(
                id = 12,
                title = "Stardew Valley",
                description = "Уютная фермерская симуляция с элементами RPG",
                price = 599.0,
                image = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTB41eALou1KF9EHpufBfhg_4zT0Es4gEgeGyWY90AyqRChsXhqNc85BEhrzeg4DfScBQ0&usqp=CAU",
                category = GameCategory.INDIE,
                genre = "Simulation",
                rating = 4.9f,
                platform = listOf(Platform.PC, Platform.PLAYSTATION, Platform.XBOX, Platform.NINTENDO_SWITCH, Platform.MOBILE),
                releaseDate = "2016",
                developer = "ConcernedApe"
            ),
            Game(
                id = 13,
                title = "Red Dead Redemption 2",
                description = "Эпическая история о жизни вне закона в Америке конца XIX века",
                price = 2499.0,
                originalPrice = 3999.0,
                image = "https://upload.wikimedia.org/wikipedia/ru/0/03/Red_Dead_Redemption_2_coverart.jpg",
                category = GameCategory.ACTION,
                genre = "Action Adventure",
                rating = 4.7f,
                platform = listOf(Platform.PC, Platform.PLAYSTATION, Platform.XBOX),
                releaseDate = "2018",
                developer = "Rockstar Games",
                isOnSale = true,
                discountPercent = 37
            ),
            Game(
                id = 14,
                title = "Minecraft",
                description = "Создавайте, исследуйте и выживайте в бесконечном мире блоков",
                price = 1299.0,
                image = "https://upload.wikimedia.org/wikipedia/ru/f/f4/Minecraft_Cover_Art.png",
                category = GameCategory.INDIE,
                genre = "Sandbox",
                rating = 4.6f,
                platform = listOf(Platform.PC, Platform.PLAYSTATION, Platform.XBOX, Platform.NINTENDO_SWITCH, Platform.MOBILE),
                releaseDate = "2011",
                developer = "Mojang Studios"
            ),
            Game(
                id = 15,
                title = "Assassin's Creed Valhalla",
                description = "Станьте легендарным викингом в этом эпическом приключении",
                price = 2799.0,
                originalPrice = 3999.0,
                image = "https://upload.wikimedia.org/wikipedia/ru/2/26/AC_Valhalla_standard_edition.jpg",
                category = GameCategory.ACTION,
                genre = "Action Adventure",
                rating = 4.3f,
                platform = listOf(Platform.PC, Platform.PLAYSTATION, Platform.XBOX),
                releaseDate = "2020",
                developer = "Ubisoft",
                isOnSale = true,
                discountPercent = 30
            )
        )
    }
    
    fun getGamesByCategory(category: GameCategory): List<Game> {
        return getAllGames().filter { it.category == category }
    }
    
    fun getGameById(id: Int): Game? {
        return getAllGames().find { it.id == id }
    }
    
    fun searchGames(query: String): List<Game> {
        return getAllGames().filter { 
            it.title.contains(query, ignoreCase = true) ||
            it.genre.contains(query, ignoreCase = true) ||
            it.developer.contains(query, ignoreCase = true)
        }
    }
    
    fun getFeaturedGames(): List<Game> {
        return getAllGames().filter { it.rating >= 4.5f }.take(6)
    }
    
    fun getSaleGames(): List<Game> {
        return getAllGames().filter { it.isOnSale }
    }
}