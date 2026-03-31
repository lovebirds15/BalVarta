package com.vp.balvarta.data

import android.net.Uri

data class Story(
    val id: Int,
    val title: String,
    val titleGujarati: String,
    val description: String,
    val duration: String,
    val audioUrl: String,
    val thumbnailResource: Int? = null
)

object StoryData {
    /**
     * GitHub path for audio files.
     * To update audio:
     * 1. Upload new .mp3 files to the 'Audio' folder in your GitHub repository.
     * 2. The BASE_AUDIO_URL below points to the 'raw' version of those files.
     */
    private const val BASE_AUDIO_URL =
        "https://raw.githubusercontent.com/lovebirds15/BalVarta/main/Audio/"

    /**
     * Helper to create a properly encoded URL for files with Gujarati characters and spaces.
     */
    private fun getUrl(fileName: String): String {
        return BASE_AUDIO_URL + Uri.encode(fileName)
    }

    val stories = listOf(
        Story(1, "The Heron, Snake and Mongoose", "બગલો, સાપ અને નોળિયો", "A tale of wisdom and friendship", "5:30", getUrl("બાળવાર્તા - 1 'બગલો, સાપ અને નોળિયો'.mp3")),
        Story(2, "The Donkey in Tiger's Skin", "વાઘના ચામડામાં રહેલો ગધેડો", "A classic story about true nature", "6:15", getUrl("બાળવાર્તા - 2 'વાઘના ચામડામાં રહેલો ગધેડો'.mp3")),
        Story(3, "Sparrow's Friends and the Foolish Monkey", "ચકલીના મિત્રો અને મૂરખ વાંદરો", "Importance of wise friends", "4:45", getUrl("બાળવાર્તા - 3 'ચકલીના મિત્રો અને મૂરખ વાંદરો'.mp3")),
        Story(4, "The Crow Couple and the Black Snake", "કાગડાનું જોડું અને કાળો નાગ", "Courage and protection", "7:20", getUrl("બાળવાર્તા - 4 'કાગડાનું જોડું અને કાળો નાગ'.mp3")),
        Story(5, "The Swan and Crow Story", "હંસ અને કાગડાની વાર્તા", "Judging others and friendship", "5:10", getUrl("બાળવાર્તા - 5 'હંસ અને કાગડાની વાર્તા'.mp3")),
        Story(6, "The Brahmin and the Crab", "બ્રાહ્મણ અને કરચલો", "Kindness and unexpected helpers", "6:00", getUrl("બાળવાર્તા - 6 'બ્રાહ્મણ અને કરચલો'.mp3")),
        Story(7, "The Story of King Supad Kanna", "સૂપડ કન્ના રાજાની વાર્તા", "A classic children's tale", "5:45", getUrl("બાળવાર્તા - 7 'સૂપડ કન્ના રાજાની વાર્તા'.mp3")),
        Story(8, "The Mouse that Became a Tiger", "વાઘ બનેલો ઉંદર અને ઋષિમુનિ", "About transformation", "4:30", getUrl("બાળવાર્તા - 8 'વાઘ બનેલો ઉંદર અને ઋષિમુનિ'.mp3")),
        Story(9, "Let Me Go to My Daughter's House", "દીકરીના ઘરે જાવા દે", "A heartwarming family story", "6:30", getUrl("બાળવાર્તા - 9 'દીકરીના ઘરે જાવા દે'.mp3")),
        Story(10, "The Fox and the Talking Cave", "શિયાળ અને તેને જવાબ આપતી ગુફા", "A clever tale about wit", "7:00", getUrl("બાળવાર્તા - 10 'શિયાળ અને તેને જવાબ આપતી ગુફા'.mp3")),
        Story(11, "The Story of Cold Tabukla", "ટાઢા ટબુકલાની વાર્તા", "An adventure in winter", "5:20", getUrl("બાળવાર્તા - 11 'ટાઢા ટબુકલાની વાર્તા'.mp3")),
        Story(12, "Sparrow's Friends and the Mad Elephant", "ચકલીના મિત્રો અને ગાંડો હાથી", "Teamwork and friendship", "4:50", getUrl("બાળવાર્તા - 12 'ચકલીના મિત્રો અને ગાંડો હાથી'.mp3")),
        Story(13, "The Rabbit Scared Away the Elephant", "સસલાએ હાથીને ભગાવ્યો", "Brave can overcome the mighty", "6:45", getUrl("બાળવાર્તા - 13 'સસલાએ હાથીને ભગાવ્યો'.mp3")),
        Story(14, "The Scared Little Rabbit", "બીકણ સસલી", "Overcoming fears", "5:00", getUrl("બાળવાર્તા - 14 'બીકણ સસલી'.mp3")),
        Story(15, "The Story of the Foolish Donkey", "મૂરખ ગધેડાની વાર્તા", "Learning from mistakes", "7:15", getUrl("બાળવાર્તા - 15 'મૂરખ ગધેડાની વાર્તા'.mp3")),
        Story(16, "The Leaf Boat, Ant and Pigeon", "પાંદડાની હોડી, કીડી અને કબૂતર", "Helping others", "6:20", getUrl("બાળવાર્તા - 16 'પાંદડાની હોડી, કીડી અને કબૂતર'.mp3")),
        Story(17, "Uncle Crow with Peanuts", "કાગડા અંકલ મમરાવાળા", "Sharing and friendship", "5:35", getUrl("બાળવાર્તા - 17 'કાગડા અંકલ મમરાવાળા'.mp3")),
        Story(18, "Whom Should the Little Mouse Marry", "ઉંદરડી કોને પરણે", "Finding the right match", "6:10", getUrl("બાળવાર્તા - 18 'ઉંદરડી કોને પરણે'.mp3")),
        Story(19, "The Story of 'I'm Falling, I'm Falling'", "પડું છું, પડું છું...ની વાર્તા", "Overcoming silly fears", "7:30", getUrl("બાળવાર્તા - 19 'પડું છું, પડું છું...ની વાર્તા'.mp3")),
        Story(20, "The Butterfly Became a Bubble", "પતંગિયું પરપોટો થઈ ગયું", "A magical transformation", "5:55", getUrl("બાળવાર્તા - 20 'પતંગિયું પરપોટો થઈ ગયું'.mp3")),
        Story(21, "Magic", "જાદુ", "A story full of wonder", "6:25", getUrl("બાળવાર્તા - 21 'જાદુ'.mp3")),
        Story(22, "The Story of Big and Small", "મોટું-પતલુંની વાર્તા", "Learning about size", "7:10", getUrl("બાળવાર્તા - 22 'મોટું-પતલુંની વાર્તા'.mp3")),
        Story(23, "The Fox Who Became King", "રાજા બનેલો શિયાળ", "Power and responsibility", "5:40", getUrl("બાળવાર્તા - 23 'રાજા બનેલો શિયાળ'.mp3")),
        Story(24, "The Story of the Innocent Camel", "ભોળા ઊંટની વાર્તા", "Trust and betrayal", "6:35", getUrl("બાળવાર્તા - 24 'ભોળા ઊંટની વાર્તા'.mp3")),
        Story(25, "The Story of Three Fish", "ત્રણ માછલીની વાર્તા", "Wisdom and preparation", "5:15", getUrl("બાળવાર્તા - 25 'ત્રણ માછલીની વાર્તા'.mp3")),
        Story(26, "The Story of Chaka and Chaki", "ચકા અને ચકીની વાર્તા", "A delightful adventure", "6:50", getUrl("બાળવાર્તા - 26 'ચકા અને ચકીની વાર્તા'.mp3")),
        Story(27, "The Frog Rode the Snake", "દેડકાએ સાપની સવારી કરી", "Cleverness overcomes danger", "5:25", getUrl("બાળવાર્તા - 27 'દેડકાએ સાપની સવારી કરી'.mp3")),
        Story(28, "Kali's Dream", "કળીને આવેલું સપનું", "A beautiful story about dreams", "6:40", getUrl("બાળવાર્તા - 28 'કળીને આવેલું સપનું'.mp3")),
        Story(29, "The Farmer and the Crane", "ખેડૂત અને સારસ પંખી", "Justice and understanding", "4:55", getUrl("બાળવાર્તા - 29 'ખેડૂત અને સારસ પંખી'.mp3")),
        Story(30, "Two Cats and the Monkey", "બે બિલાડી અને વાંદરો", "Greed and sharing", "6:15", getUrl("બાળવાર્તા - 30 'બે બિલાડી અને વાંદરો'.mp3")),
        Story(31, "The Story of Dala Tarvadi", "દલા તરવાડીની વાર્તા", "Traditional Gujarati folk tale", "5:30", getUrl("બાળવાર્તા - 31 'દલા તરવાડીની વાર્તા'.mp3")),
        Story(32, "The Peacock's Complaint", "મોરની ફરિયાદ", "Being grateful", "7:05", getUrl("બાળવાર્તા - 32 'મોરની ફરિયાદ'.mp3")),
        Story(33, "I Am Doing Thaga Thaiya", "ઠાગા ઠૈયા કરું છું", "Playful story with rhythm", "5:50", getUrl("બાળવાર્તા - 33 'ઠાગા ઠૈયા કરું છું'.mp3")),
        Story(34, "Daghiya's Crooked Tail", "ડાઘિયાની પૂંછડી વાંકી", "Accepting differences", "6:30", getUrl("બાળવાર્તા - 34 'ડાઘિયાની પૂંછડી વાંકી'.mp3")),
        Story(35, "Chogala, Now Stop!", "છોગાળા, હવે છોડો !", "Learning when to listen", "5:20", getUrl("બાળવાર્તા - 35 'છોગાળા, હવે છોડો !'.mp3")),
        Story(36, "The Happy Crow", "આનંદી કાગડો", "Finding joy in simple things", "6:45", getUrl("બાળવાર્તા - 36 'આનંદી કાગડો'.mp3")),
        Story(37, "The Donkey and the Flower", "ગધેડો અને ફૂલ", "Beauty and inner qualities", "5:35", getUrl("બાળવાર્તા - 37 'ગધેડો અને ફૂલ'.mp3")),
        Story(38, "The Ant from Mumbai", "મુંબઈની કીડી", "City and village life", "6:20", getUrl("બાળવાર્તા - 38 'મુંબઈની કીડી'.mp3")),
        Story(39, "The Duckling", "બતકનું બચ્ચું", "Growing up and finding your place", "5:35", getUrl("બાળવાર્તા - 39 'બતકનું બચ્ચું'.mp3")),
        Story(40, "Three Brothers", "ત્રણ ભાઈબંધ", "Unity and brotherhood", "7:40", getUrl("બાળવાર્તા - 40 'ત્રણ ભાઈબંધ'.mp3"))
    )
}
