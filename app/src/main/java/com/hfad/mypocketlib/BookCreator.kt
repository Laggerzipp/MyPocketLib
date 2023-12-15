package com.hfad.mypocketlib

import com.hfad.mypocketlib.database.Book

class BookCreator {
    fun createBookList():List<Book>{
        return listOf(
            Book(
                null, R.drawable.book_grugoj_mir_artifactor ,
                "Another World: Artifactor" ,"Ivan Gorodetsky",
                "Once he was a simple loser on Earth. Women didn't love him, but he always protected them. In the end, he was just killed because his wife needed his apartment, and the pathetic disabled man was simply a burden to her. But this arrangement did not please those who could control the fates of people, and as a result, the protagonist was sent to a world of magic. Here, the man was reborn in a new body.\n" +
                        "\n" +
                        "In this world, he is a true ladies' man. The transmigrant has already arrived in the capital, overcoming many obstacles along the way. His life has been threatened by mortal danger more than once. Now, however, the transmigrant needs to settle in a new place and start a profitable business. The thing is, the protagonist plans to enroll in the Academy of Magic, and since his magical gift is still weak, he will have to pay for his education. So, he needs to save money for tuition and try not to get into trouble during this time, avoiding any intrigues that could jeopardize his future.",
                "★★★☆☆", "Russian",
                "https://www.dropbox.com/scl/fi/m2bqi6kttowgbdvibzpcb/02_Another-WorldArtifactor.pdf?rlkey=4kynrfqym9whhl70ull2s7en7&dl=0"
            ),
            Book(
                null, R.drawable.book_drugoj_mir_popadanec ,
                "Another World: Traveller" ,"Ivan Gorodetsky",
                "The Observer sent Nikolai Temnov, a 26-year-old guy, into the body of a 16-year-old boy." +
                        " He lives in a magical world and suffered from a magical blow during a duel." +
                        " When sending Temnov to the new world, " +
                        "The Observer gave him the opportunity to choose a supernatural ability." +
                        " He ended up with the ability to attract the attention of the opposite sex." +
                        " This greatly surprised Nikolai, as in our world," +
                        " women always considered him a pushover and used him." +
                        " Now he is Tyrr Allin Merdgress, the son of a local aristocrat who lacks magical talent." +
                        " So, the transmigrant will need to first awaken his magical gift," +
                        " or else he will be deprived of his inheritance." +
                        " Well, then we can see what he will do in this unusual world for him," +
                        " especially since The Observer made him a born ladies' man!",
                "★★★☆☆", "Russian",
                "https://www.dropbox.com/scl/fi/sqiwvcxnopwseacj2f0pv/01_Another-WorldTraveller.pdf?rlkey=nmr3oqzzo4am51h4o1wmpo9om&dl=0"
            ),
            Book(
                null, R.drawable.book_after_transformation_mine_and_her_wild_fantasy,
                "After Transformation Mine and Her Wild Fantasy","Xue Yan Tian Zhao",
                "Me? I wake up to find myself transformed into two different bodies." +
                        "Both a male body and a female body, one conscience operating two bodies, all sorts of embarrassing moments." +
                        "Walking together normally makes other people think that I am sweethearts with my other-self, such sadness." +
                        "Also, the entire business of a hero rescuing the beautiful maiden, I call Bull." +
                        "I get slashed by a sword and my female body gets abducted, and he wants me to thank him?!?! Can it be less of a tragedy?",
                "★★★★☆", "English",
                "https://www.dropbox.com/scl/fi/itvk9w4sal9opzdfdup1h/After-Transformation-Mine-and-Her-Wild-Fantasy.pdf?rlkey=wl4zb8kcysik8ts0ej45oqhp4&dl=0"
            ))
    }
}