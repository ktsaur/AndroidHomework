package ru.itis.homework2.repositories

import android.content.Context
import ru.itis.homework2.models.ListPictureItemModel

class ContentRepository {

    fun getListContent(context: Context): List<ListPictureItemModel> = listOf(
        ListPictureItemModel(
            imageUrl = "https://f4.bcbits.com/img/a1834161590_10.jpg",
            title = "\"Why Lawd?\"",
            description = "NxWorries: \"Why Lawd?\" — это дебютный студийный альбом американского дуэта NxWorries, состоящего из продюсера Knxwledge и рэпера Anderson .Paak. Альбом был выпущен в 2024 году и представляет собой смесь хип-хопа, R&B и соула, с элементами ретро-звучания и модернистского подхода.",
        ),

        ListPictureItemModel(
            imageUrl = "https://cdns-images.dzcdn.net/images/cover/a85bd338d7adbcd17251128f135371b0/1900x1900-000000-80-0-0.jpg",
            title = "\"Baw Baw Black Sheep\"",
            description = "\"Baw Baw Black Sheep\" — это дебютный студийный альбом ирландского рэпера Rejjie Snow, выпущенный 27 марта 2020 года. Альбом получил положительные отзывы от критиков и был высоко оценен за свою музыкальную глубину, а также за подход к лирике и экспериментальное звучание."
        ),
        ListPictureItemModel(
            imageUrl = "https://the-flow.ru/uploads/images/catalog/element/5e2164230fa8e.jpg",
            title = "\"Circles\"",
            description = "\"Circles\" — это посмертный альбом американского рэпера и музыканта Mac Miller, выпущенный 17 января 2020 года, спустя несколько месяцев после его трагической смерти. Альбом был завершен после его смерти, с участием продюсера Jon Brion, с которым Mac Miller работал над проектом. \"Circles\" стал логическим продолжением его предыдущего альбома \"Swimming\" (2018), и по своей атмосфере и звучанию он продолжает исследовать темы саморазмышлений, борьбы с внутренними демонами и поисков смысла жизни."
        ),

        ListPictureItemModel(
            imageUrl = "https://the-flow.ru/uploads/images/catalog/element/57b7a50009ae5.jpg",
            title = "\"The Divine Feminine\"",
            description = "\"The Divine Feminine\" — это четвёртый студийный альбом американского рэпера и музыканта Mac Miller, выпущенный 16 сентября 2016 года. Этот альбом стал важным этапом в его музыкальной карьере, так как он отошел от привычного для себя звучания, добавив в свой репертуар элементы соула, R&B и поп-музыки. Альбом также отличается от предыдущих работ тем, что в нем прослеживаются темы любви, отношений и духовности."
        ),

        ListPictureItemModel(
            imageUrl = "https://media.pitchfork.com/photos/5929c3c1eb335119a49ed7b4/master/pass/aa7d1929.jpg",
            title = "\"This Old Dog\"",
            description = "\"This Old Dog\" — третий студийный альбом канадского музыканта Mac DeMarco, выпущенный 5 мая 2017 года. В этом альбоме Мак показал новую сторону своего музыкального стиля, перейдя к более спокойному и зрелому звучанию. \"This Old Dog\" выделяется тем, что фокусируется на более личных и откровенных темах, и стал одним из самых глубоко проработанных и эмоционально насыщенных альбомов DeMarco."
        ),

        ListPictureItemModel(
            imageUrl = "https://media.pitchfork.com/photos/5cafb5476f99665c10beeb81/master/pass/MacDemarco_HereComesTheCowboy.jpg",
            title = "\"Here Comes the Cowboy\"",
            description = "\"Here Comes the Cowboy\" — четвертый студийный альбом канадского музыканта Mac DeMarco, выпущенный 10 мая 2019 года. В этом альбоме Мак сохраняет свой фирменный минималистичный и расслабленный стиль, добавляя новые нюансы и элементы, вдохновленные кантри и западной культурой. Альбом стал одним из самых неоднозначных в его карьере, с акцентом на меланхоличное и медитативное звучание, уходящее от легкости предыдущих работ."
        ),

        ListPictureItemModel(
            imageUrl = "https://cdplak.com/image/cache/catalog/ason2/son11/Arctic-monkeys-AM-cd-kapak-on-600x600.jpg",
            title = "\"AM\"",
            description = "\"AM\" — пятый студийный альбом британской рок-группы Arctic Monkeys, выпущенный 9 сентября 2013 года. Этот альбом стал важной вехой в их карьере, принеся им международное признание и новый уровень популярности. Альбом сочетает элементы рок-н-ролла, хип-хопа, психоделического рока и R&B, что делает его уникальным в творчестве группы. С помощью \"AM\" Arctic Monkeys смогли не только удержать внимание своей аудитории, но и привлечь новых слушателей благодаря новаторскому подходу к звучанию."
        ),

        ListPictureItemModel(
            imageUrl = "https://cdns-images.dzcdn.net/images/cover/a7a16b8f63b1ec0e9fbd327619966737/0x1900-000000-80-0-0.jpg",
            title = "\"Flower Boy\"",
            description = "\"Flower Boy\" (иногда известный как \"Scum Fuck Flower Boy\") — это четвёртый студийный альбом американского рэпера Tyler, The Creator, выпущенный 21 июля 2017 года. Этот альбом стал знаковым для его карьеры, так как показал новую сторону Тайлеру как артиста, с акцентом на лирику, эмоциональную открытость и музыкальный продакшн. \"Flower Boy\" привнёс в его творчество зрелость и позволил рассмотреть глубину его внутреннего мира."
        ),

        ListPictureItemModel(
            imageUrl = "https://wow.fan/cdn/shop/files/19620-image-1_cfa5a58e-f8f8-4171-850c-f87353ec583d.jpg",
            title = "\"Born Sinner\"",
            description = "\"Born Sinner\" — второй студийный альбом американского рэпера J. Cole, выпущенный 18 июня 2013 года. В этом альбоме J. Cole исследует темы борьбы с искушениями, внутренней борьбы и личных слабостей, играя на двойственности между добром и злом. Альбом стал важным этапом в его карьере, показав более зрелое и глубокое видение жизни и музыки."
        ),

    )

}