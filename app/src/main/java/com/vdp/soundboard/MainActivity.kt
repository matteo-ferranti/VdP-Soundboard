package com.vdp.soundboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vdp.soundboard.ui.theme.VdPSoundboardTheme
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import android.media.MediaPlayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.layout.width
import android.content.Intent
import androidx.core.content.FileProvider
import java.io.File
import androidx.compose.foundation.Canvas
import androidx.compose.material3.OutlinedTextField
import kotlin.collections.listOf
import androidx.compose.material3.IconButton
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.ui.text.font.FontStyle


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        val prefs = getSharedPreferences("favorites", MODE_PRIVATE)

        val savedFavorites = prefs
            .getStringSet("favorite_audio_res", emptySet())
            ?.mapNotNull { it.toIntOrNull() }
            ?.toSet()
            ?: emptySet()

        favoriteAudioRes = savedFavorites

        setContent {
            VdPSoundboardTheme {

                var currentPage by remember {
                    mutableStateOf("home")
                }

                when (currentPage) {

                    "home" -> HomePage(
                        onAllStarsClick = {
                            currentPage = "allstars"
                        },
                        onVideoGamesClick = {
                            currentPage = "videogames"
                        },
                        onMovieStarsClick = {
                            currentPage = "moviestars"
                        },
                        onClassicMemeClick = {
                            currentPage = "classicmeme"
                        },
                        onFavoritesClick = {
                            currentPage = "favorites" }
                    )

                    "allstars" -> AllStarsPage(
                        onBackClick = {
                            currentPage = "home"
                        }
                    )

                    "videogames" -> VideoGamesPage(
                        onBackClick = {
                            currentPage = "home"
                        }
                    )

                    "moviestars" -> MovieStarsPage(
                        onBackClick = {
                            currentPage = "home"
                        }
                    )

                    "classicmeme" -> ClassicMemePage(
                        onBackClick = {
                            currentPage = "home"
                        }
                    )

                    "favorites" -> FavoritesPage(
                        onBackClick = {
                            currentPage = "home"
                        }
                    )

                }
            }
        }
    }
}


// --------------------------------------------------
// FONT
// --------------------------------------------------

val bungeeFontFamily = FontFamily(
    Font(R.font.bungee)
)

val soundFontFamily = FontFamily(
    Font(R.font.blinker)
)


// --------------------------------------------------
// MODELLO SUONO
// --------------------------------------------------

data class SoundItem(
    val title: String,
    val imageRes: Int,
    val audioRes: Int,
    val audioExtension: String = "mp3",
    val searchTags: List<String> = emptyList()
)

var favoriteAudioRes by mutableStateOf(setOf<Int>())

val allStarsSounds = listOf(

    SoundItem(
        title = "Due Milioni di Euro",
        imageRes = R.drawable.vdp_duemilioni,
        audioRes = R.raw.duemilioni,
        searchTags = listOf("alessandro orlando")
    ),

    SoundItem(
        title = "Force of Nature",
        imageRes = R.drawable.vdp_scout,
        audioRes = R.raw.forcenature,
        searchTags = listOf("scout", "team fortress", "tf2")
    ),

    SoundItem(
        title = "Gloria",
        imageRes = R.drawable.vdp_fordring,
        audioRes = R.raw.gloria,
        searchTags = listOf("tirion fordring", "world of warcraft", "wow")

    ),

    SoundItem(
        title = "Sta entrando un Ne-",
        imageRes = R.drawable.vdp_neg,
        audioRes = R.raw.neg,
        searchTags = listOf("peter griffin")

    ),

    SoundItem(
        title = "Did you forget?",
        imageRes = R.drawable.vdp_spy,
        audioRes = R.raw.forget,
        searchTags = listOf("spy", "team fortress", "tf2")

    ),

    SoundItem(
        title = "Why are you gay?",
        imageRes = R.drawable.vdp_gay,
        audioRes = R.raw.gay

    ),

    SoundItem(
        title = "Chaddone",
        imageRes = R.drawable.vdp_chad,
        audioRes = R.raw.chad

    ),

    SoundItem(
        title = "Disappointment",
        imageRes = R.drawable.vdp_spy,
        audioRes = R.raw.disappointment,
        searchTags = listOf("spy", "team fortress", "tf2")

    ),

    SoundItem(
        title = "Non dire parolacce",
        imageRes = R.drawable.vdp_enzo,
        audioRes = R.raw.parolacce,
        searchTags = listOf("enzo il matto", "ostia")

    ),

    SoundItem(
        title = "È notte fonda",
        imageRes = R.drawable.vdp_messere,
        audioRes = R.raw.nottefonda,
        searchTags = listOf("messere", "this is life", "villa del presidente", "vdp")

    ),

    SoundItem(
        title = "Gimme Money",
        imageRes = R.drawable.vdp_bobo,
        audioRes = R.raw.gimmemoney,
        searchTags = listOf("bobo rondelli")

    ),

    SoundItem(
        title = "A coward... PUAH!",
        imageRes = R.drawable.vdp_garrosh,
        audioRes = R.raw.coward,
        searchTags = listOf("garrosh hellscream", "world of warcraft", "wow")

    ),

    SoundItem(
        title = "You cannot kill hope",
        imageRes = R.drawable.vdp_saurfang,
        audioRes = R.raw.killhope,
        searchTags = listOf("saurfang", "world of warcraft", "wow")

    ),

    SoundItem(
        title = "Quest complete",
        imageRes = R.drawable.vdp_quest,
        audioRes = R.raw.quest,
        searchTags = listOf("world of warcraft", "wow")

    ),

    SoundItem(
        title = "Ridi Pagliaccio",
        imageRes = R.drawable.vdp_pagliaccio,
        audioRes = R.raw.ridipagliaccio,
        searchTags = listOf("luciano pavarotti", "musica classica")

    ),

    SoundItem(
        title = "Arrivederci",
        imageRes = R.drawable.vdp_arrivederci,
        audioRes = R.raw.arrivederci,
        searchTags = listOf("jojo", "bruno bucciarati")

    ),

    SoundItem(
        title = "Devi morire",
        imageRes = R.drawable.vdp_messere,
        audioRes = R.raw.devimorire,
        searchTags = listOf("messere", "this is life", "villa del presidente", "vdp")


    ),

    SoundItem(
        title = "You disgust me",
        imageRes = R.drawable.vdp_spy,
        audioRes = R.raw.disgust,
        searchTags = listOf("spy", "team fortress", "tf2")

    ),

    SoundItem(
        title = "Brutta Gente",
        imageRes = R.drawable.vdp_pyke,
        audioRes = R.raw.bruttagente,
        searchTags = listOf("pyke", "league of legends", "lol")

    ),

    SoundItem(
        title = "PLUS ULTRA",
        imageRes = R.drawable.vdp_plusultra,
        audioRes = R.raw.plusultra,
        searchTags = listOf("endeavor", "my hero academia", "mha")

    ),

    SoundItem(
        title = "We fight to the end",
        imageRes = R.drawable.vdp_fordring,
        audioRes = R.raw.fight,
        searchTags = listOf("tirion fordring", "world of warcraft", "wow")

    ),

    SoundItem(
        title = "Me ne vado?",
        imageRes = R.drawable.vdp_berlusconi,
        audioRes = R.raw.menevado,
        searchTags = listOf("berlusconi", "cavaliere", "presidente")

    ),

    SoundItem(
        title = "Nessuna pietà",
        imageRes = R.drawable.vdp_pantheon,
        audioRes = R.raw.nessunapieta,
        searchTags = listOf("pantheon", "league of legends", "lol")

    ),

    SoundItem(
        title = "Watch your clever mouth",
        imageRes = R.drawable.vdp_garrosh,
        audioRes = R.raw.clevermouth,
        searchTags = listOf("garrosh hellscream", "world of warcraft", "wow")

    ),

    SoundItem(
        title = "Va' alla malora",
        imageRes = R.drawable.vdp_conan,
        audioRes = R.raw.malora,
        searchTags = listOf("conan il barbaro", "arnold schwarzenegger")

    ),

    SoundItem(
        title = "Sei bravo a parole",
        imageRes = R.drawable.vdp_moccia,
        audioRes = R.raw.pugni,
        searchTags = listOf("dario moccia", "pugni")

    ),

    SoundItem(
        title = "Tu succhi?",
        imageRes = R.drawable.vdp_cazzi,
        audioRes = R.raw.succhi,
        searchTags = listOf("sergente maggiore hartman", "full metal jacket", "stanley kubrick")

    ),

    SoundItem(
        title = "It's the Capricorn",
        imageRes = R.drawable.vdp_capricorn,
        audioRes = R.raw.capricorn,
        searchTags = listOf("guild wars 2", "gw2")

    ),

    SoundItem(
        title = "El Racista",
        imageRes = R.drawable.vdp_elracista,
        audioRes = R.raw.elracista,
        searchTags = listOf("femboy")

    ),

    SoundItem(
        title = "Za Warudo",
        imageRes = R.drawable.vdp_zawarudo,
        audioRes = R.raw.zawarudo,
        searchTags = listOf("jojo", "dio brando")

    ),

    SoundItem(
        title = "Figlio del Fordragone",
        imageRes = R.drawable.vdp_carino,
        audioRes = R.raw.carino,
        searchTags = listOf("carino")

    ),

    SoundItem(
        title = "MRGRLGRL",
        imageRes = R.drawable.vdp_murloc,
        audioRes = R.raw.murloc,
        searchTags = listOf("murloc", "world of warcraft", "wow")

    ),

    SoundItem(
        title = "I have been called",
        imageRes = R.drawable.vdp_reinhardt,
        audioRes = R.raw.always,
        searchTags = listOf("reinhardt", "overwatch", "ow")

    ),

    SoundItem(
        title = "Relaxing Jazz",
        imageRes = R.drawable.vdp_jazz,
        audioRes = R.raw.jazz

    ),

    SoundItem(
        title = "Keep cryin' baby",
        imageRes = R.drawable.vdp_heavy,
        audioRes = R.raw.keepcrying,
        searchTags = listOf("heavy", "team fortress 2", "tf2")

    ),

    SoundItem(
        title = "Baby Shark",
        imageRes = R.drawable.vdp_babyshark,
        audioRes = R.raw.babyshark,
        searchTags = listOf("messere", "maid")

    ),

    SoundItem(
        title = "Per favore non piangere",
        imageRes = R.drawable.vdp_nonpiangere,
        audioRes = R.raw.nonpiangere

    ),

    SoundItem(
        title = "Unworthy",
        imageRes = R.drawable.vdp_alarak,
        audioRes = R.raw.unworthy,
        searchTags = listOf("alarak", "starcraft", "sc")

    ),

    SoundItem(
        title = "Objection!",
        imageRes = R.drawable.vdp_objection,
        audioRes = R.raw.objection,
        searchTags = listOf("phoenix wright", "ace attorney")

    ),

    SoundItem(
        title = "Lisciami le Mele",
        imageRes = R.drawable.vdp_zeb,
        audioRes = R.raw.mele,
        searchTags = listOf("zeb89", "kenneth caselli")

    ),

    SoundItem(
        title = "Put your Faith in the Light",
        imageRes = R.drawable.vdp_fordring,
        audioRes = R.raw.light,
        searchTags = listOf("tirion fordring", "world of warcraft", "wow")

    ),

    SoundItem(
        title = "Non ci sto",
        imageRes = R.drawable.vdp_cicciogamer,
        audioRes = R.raw.provvedimenti,
        searchTags = listOf("cicciogamer89", "provvedimenti")

    ),

    SoundItem(
        title = "Quant'è bella la Mafia",
        imageRes = R.drawable.vdp_aggmafia,
        audioRes = R.raw.mafia,
        searchTags = listOf("aldo giovanni e giacomo", "agg", "la leggenda di al john e jack")

    ),


    SoundItem(
        title = "Porco...",
        imageRes = R.drawable.vdp_sabaku,
        audioRes = R.raw.sabaku,
        searchTags = listOf("sabaku", "bestemmia")

    ),

    SoundItem(
        title = "AAAAAAAAAAH",
        imageRes = R.drawable.vdp_goblin,
        audioRes = R.raw.goblin,
        searchTags = listOf("goblin", "urlo", "strillo")

    ),

    SoundItem(
        title = "Inettitudine",
        imageRes = R.drawable.vdp_alarak,
        audioRes = R.raw.inettitudine,
        searchTags = listOf("alarak", "starcraft", "sc")

    )

    )

val warcraftSounds = listOf(

    SoundItem(
        title = "Gloria",
        imageRes = R.drawable.vdp_fordring,
        audioRes = R.raw.gloria,
        searchTags = listOf("tirion fordring", "world of warcraft", "wow")
    ),

    SoundItem(
        title = "We fight to the end",
        imageRes = R.drawable.vdp_fordring,
        audioRes = R.raw.fight,
        searchTags = listOf("tirion fordring", "world of warcraft", "wow")
    ),

    SoundItem(
        title = "Put your Faith in the Light",
        imageRes = R.drawable.vdp_fordring,
        audioRes = R.raw.light,
        searchTags = listOf("tirion fordring", "world of warcraft", "wow")
    ),

    SoundItem(
        title = "MRGRLGRL",
        imageRes = R.drawable.vdp_murloc,
        audioRes = R.raw.murloc,
        searchTags = listOf("murloc", "world of warcraft", "wow")

    ),

    SoundItem(
        title = "Watch your clever mouth",
        imageRes = R.drawable.vdp_garrosh,
        audioRes = R.raw.clevermouth,
        searchTags = listOf("garrosh hellscream", "world of warcraft", "wow")

    ),

    SoundItem(
        title = "A coward... PUAH!",
        imageRes = R.drawable.vdp_garrosh,
        audioRes = R.raw.coward,
        searchTags = listOf("garrosh hellscream", "world of warcraft", "wow")

    ),

    SoundItem(
        title = "Sei CONGEDATO!",
        imageRes = R.drawable.vdp_garrosh,
        audioRes = R.raw.congedato,
        searchTags = listOf("garrosh hellscream", "world of warcraft", "wow")

    ),

    SoundItem(
        title = "You are DISMISSED!",
        imageRes = R.drawable.vdp_garrosh,
        audioRes = R.raw.dismissed,
        searchTags = listOf("garrosh hellscream", "world of warcraft", "wow")

    ),

    SoundItem(
        title = "You cannot kill hope",
        imageRes = R.drawable.vdp_saurfang,
        audioRes = R.raw.killhope,
        searchTags = listOf("saurfang", "world of warcraft", "wow")

    ),

    SoundItem(
        title = "Quest complete",
        imageRes = R.drawable.vdp_quest,
        audioRes = R.raw.quest,
        searchTags = listOf("world of warcraft", "wow")

    ),

    SoundItem(
        title = "You are not prepared",
        imageRes = R.drawable.vdp_illidan,
        audioRes = R.raw.prepared,
        searchTags = listOf("illidan stormrage", "world of warcraft", "wow")

    ),

    SoundItem(
        title = "Frostmourne hungers",
        imageRes = R.drawable.vdp_lichking,
        audioRes = R.raw.frostmourne,
        searchTags = listOf("arthas menethil", "lich king", "world of warcraft", "wow")

    ),

    SoundItem(
        title = "I AM THE CATACLYSM",
        imageRes = R.drawable.vdp_deathwing,
        audioRes = R.raw.cataclysm,
        searchTags = listOf("deathwing", "neltharion", "world of warcraft", "wow")

    ),

    SoundItem(
        title = "Time is money, friend",
        imageRes = R.drawable.vdp_wowgoblin,
        audioRes = R.raw.timeismoney,
        searchTags = listOf("goblin", "world of warcraft", "wow")

    ),

    SoundItem(
        title = "Work Work",
        imageRes = R.drawable.vdp_peone,
        audioRes = R.raw.work,
        searchTags = listOf("peone", "world of warcraft", "wow", "warcraft 3", "wc3")

    )

)

val lolSounds = listOf(

    SoundItem(
        title = "Nessuna pietà",
        imageRes = R.drawable.vdp_pantheon,
        audioRes = R.raw.nessunapieta,
        searchTags = listOf("pantheon", "league of legends", "lol")

    ),

    SoundItem(
        title = "Brutta Gente",
        imageRes = R.drawable.vdp_pyke,
        audioRes = R.raw.bruttagente,
        searchTags = listOf("pyke", "league of legends", "lol")
    ),

    SoundItem(
        title = "Se i Cervelli fossero Proiettili",
        imageRes = R.drawable.vdp_tahmkench,
        audioRes = R.raw.cervelli,
        searchTags = listOf("tahm kench", "league of legends", "lol")
    ),

    SoundItem(
        title = "Buongiorno Fiorellino",
        imageRes = R.drawable.vdp_mundo,
        audioRes = R.raw.fiorellino,
        searchTags = listOf("dottor mundo", "league of legends", "lol")
    ),

    SoundItem(
        title = "Tutututututu",
        imageRes = R.drawable.vdp_twitch,
        audioRes = R.raw.tututu,
        searchTags = listOf("twitch", "league of legends", "lol")
    ),

    SoundItem(
        title = "Ero nascosto",
        imageRes = R.drawable.vdp_twitch,
        audioRes = R.raw.nascosto,
        searchTags = listOf("twitch", "league of legends", "lol")
    ),

    SoundItem(
        title = "OK",
        imageRes = R.drawable.vdp_rammus,
        audioRes = R.raw.ok,
        searchTags = listOf("rammus", "league of legends", "lol")
    ),

    SoundItem(
        title = "Folle",
        imageRes = R.drawable.vdp_thresh,
        audioRes = R.raw.folle,
        searchTags = listOf("thresh", "league of legends", "lol")
    )

)


val teamfortressSounds = listOf(

    SoundItem(
        title = "Force of Nature",
        imageRes = R.drawable.vdp_scout,
        audioRes = R.raw.forcenature,
        searchTags = listOf("scout", "team fortress", "tf2")
    ),

    SoundItem(
        title = "BONK!",
        imageRes = R.drawable.vdp_scout,
        audioRes = R.raw.bonk,
        audioExtension = "wav",
        searchTags = listOf("scout", "team fortress", "tf2")
    ),

    SoundItem(
        title = "This sucks on ice!",
        imageRes = R.drawable.vdp_scout,
        audioRes = R.raw.sucksice,
        audioExtension = "wav",
        searchTags = listOf("scout", "team fortress", "tf2")
    ),

    SoundItem(
        title = "Un-freakin'-touchable",
        imageRes = R.drawable.vdp_scout,
        audioRes = R.raw.untouchable,
        audioExtension = "wav",
        searchTags = listOf("scout", "team fortress", "tf2")
    ),

    SoundItem(
        title = "Paying attention",
        imageRes = R.drawable.vdp_scout,
        audioRes = R.raw.attention,
        audioExtension = "wav",
        searchTags = listOf("scout", "team fortress", "tf2")
    ),

    SoundItem(
        title = "Maggots",
        imageRes = R.drawable.vdp_soldier,
        audioRes = R.raw.maggots,
        audioExtension = "wav",
        searchTags = listOf("soldier", "team fortress", "tf2")
    ),

    SoundItem(
        title = "Maybe even the best",
        imageRes = R.drawable.vdp_soldier,
        audioRes = R.raw.maybethebest,
        audioExtension = "wav",
        searchTags = listOf("soldier", "team fortress", "tf2")
    ),

    SoundItem(
        title = "You deserve a Medal",
        imageRes = R.drawable.vdp_soldier,
        audioRes = R.raw.medal,
        audioExtension = "wav",
        searchTags = listOf("soldier", "team fortress", "tf2")
    ),

    SoundItem(
        title = "Glue you back together",
        imageRes = R.drawable.vdp_demo,
        audioRes = R.raw.glueyou,
        audioExtension = "wav",
        searchTags = listOf("demoman", "team fortress", "tf2")
    ),

    SoundItem(
        title = "Only one",
        imageRes = R.drawable.vdp_demo,
        audioRes = R.raw.onlyone,
        audioExtension = "wav",
        searchTags = listOf("demoman", "team fortress", "tf2")
    ),

    SoundItem(
        title = "Burn in Hell",
        imageRes = R.drawable.vdp_demo,
        audioRes = R.raw.burninhell,
        audioExtension = "wav",
        searchTags = listOf("demoman", "team fortress", "tf2")
    ),

    SoundItem(
        title = "Cheers, mate",
        imageRes = R.drawable.vdp_demo,
        audioRes = R.raw.cheers,
        audioExtension = "wav",
        searchTags = listOf("demoman", "team fortress", "tf2")
    ),

    SoundItem(
        title = "Keep cryin' baby",
        imageRes = R.drawable.vdp_heavy,
        audioRes = R.raw.keepcrying,
        audioExtension = "wav",
        searchTags = listOf("heavy", "team fortress", "tf2")

    ),

    SoundItem(
        title = "Cry some more",
        imageRes = R.drawable.vdp_heavy,
        audioRes = R.raw.crymore,
        audioExtension = "wav",
        searchTags = listOf("heavy", "team fortress", "tf2")

    ),

    SoundItem(
        title = "Ya-ta-da-Kaboom",
        imageRes = R.drawable.vdp_heavy,
        audioRes = R.raw.kaboom,
        audioExtension = "wav",
        searchTags = listOf("heavy", "team fortress", "tf2")

    ),

    SoundItem(
        title = "You are dead, not big surprise",
        imageRes = R.drawable.vdp_heavy,
        audioRes = R.raw.nosurprise,
        audioExtension = "wav",
        searchTags = listOf("heavy", "team fortress", "tf2")

    ),

    SoundItem(
        title = "Not big surprise",
        imageRes = R.drawable.vdp_heavy,
        audioRes = R.raw.notbigsurprise,
        searchTags = listOf("heavy", "team fortress", "tf2")

    ),

    SoundItem(
        title = "Never make me angry",
        imageRes = R.drawable.vdp_heavy,
        audioRes = R.raw.angry,
        audioExtension = "wav",
        searchTags = listOf("heavy", "team fortress", "tf2")

    ),

    SoundItem(
        title = "How could this happen?",
        imageRes = R.drawable.vdp_heavy,
        audioRes = R.raw.howhappen,
        audioExtension = "wav",
        searchTags = listOf("heavy", "team fortress", "tf2")

    ),

    SoundItem(
        title = "Not usually my job",
        imageRes = R.drawable.vdp_heavy,
        audioRes = R.raw.notmyjob,
        searchTags = listOf("heavy", "team fortress", "tf2")

    ),

    SoundItem(
        title = "Thanks, Mister",
        imageRes = R.drawable.vdp_engi,
        audioRes = R.raw.thanks,
        audioExtension = "wav",
        searchTags = listOf("engineer", "team fortress", "tf2")

    ),

    SoundItem(
        title = "Another satisfied Customer",
        imageRes = R.drawable.vdp_engi,
        audioRes = R.raw.customer,
        audioExtension = "wav",
        searchTags = listOf("engineer", "team fortress", "tf2")

    ),

    SoundItem(
        title = "A little less Gun",
        imageRes = R.drawable.vdp_engi,
        audioRes = R.raw.lessgun,
        audioExtension = "wav",
        searchTags = listOf("engineer", "team fortress", "tf2")

    ),

    SoundItem(
        title = "Drunk on the Battlefield",
        imageRes = R.drawable.vdp_engi,
        audioRes = R.raw.drunk,
        audioExtension = "wav",
        searchTags = listOf("engineer", "team fortress", "tf2")

    ),

    SoundItem(
        title = "That just ain't right",
        imageRes = R.drawable.vdp_engi,
        audioRes = R.raw.aintright,
        audioExtension = "wav",
        searchTags = listOf("engineer", "team fortress", "tf2")

    ),

    SoundItem(
        title = "Schweinhunds",
        imageRes = R.drawable.vdp_medic,
        audioRes = R.raw.schweinhunds,
        audioExtension = "wav",
        searchTags = listOf("medic", "team fortress", "tf2")

    ),

    SoundItem(
        title = "Gesundheit",
        imageRes = R.drawable.vdp_medic,
        audioRes = R.raw.gesundheit,
        audioExtension = "wav",
        searchTags = listOf("medic", "team fortress", "tf2")

    ),

    SoundItem(
        title = "I am melting",
        imageRes = R.drawable.vdp_medic,
        audioRes = R.raw.melting,
        audioExtension = "wav",
        searchTags = listOf("medic", "team fortress", "tf2")

    ),

    SoundItem(
        title = "Auf Wiedersehen",
        imageRes = R.drawable.vdp_medic,
        audioRes = R.raw.aufwiedersehen,
        audioExtension = "wav",
        searchTags = listOf("medic", "team fortress", "tf2")

    ),

    SoundItem(
        title = "Piss off, you mongrels",
        imageRes = R.drawable.vdp_sniper,
        audioRes = R.raw.pissoff,
        audioExtension = "wav",
        searchTags = listOf("sniper", "team fortress", "tf2")

    ),

    SoundItem(
        title = "G'day!",
        imageRes = R.drawable.vdp_sniper,
        audioRes = R.raw.gday,
        audioExtension = "wav",
        searchTags = listOf("sniper", "team fortress", "tf2")

    ),

    SoundItem(
        title = "Appreciate it, mate",
        imageRes = R.drawable.vdp_sniper,
        audioRes = R.raw.appreciate,
        audioExtension = "wav",
        searchTags = listOf("sniper", "team fortress", "tf2")

    ),

    SoundItem(
        title = "Outta bed",
        imageRes = R.drawable.vdp_sniper,
        audioRes = R.raw.outtabed,
        audioExtension = "wav",
        searchTags = listOf("sniper", "team fortress", "tf2")

    ),

    SoundItem(
        title = "Bloody Heel, you're awful",
        imageRes = R.drawable.vdp_sniper,
        audioRes = R.raw.awful,
        audioExtension = "wav",
        searchTags = listOf("sniper", "team fortress", "tf2")

    ),

    SoundItem(
        title = "See you in five minutes",
        imageRes = R.drawable.vdp_sniper,
        audioRes = R.raw.fiveminutes,
        audioExtension = "wav",
        searchTags = listOf("sniper", "team fortress", "tf2")

    ),

    SoundItem(
        title = "Skill always beats Luck",
        imageRes = R.drawable.vdp_sniper,
        audioRes = R.raw.skill,
        audioExtension = "wav",
        searchTags = listOf("sniper", "team fortress", "tf2")

    ),

    SoundItem(
        title = "Did you forget?",
        imageRes = R.drawable.vdp_spy,
        audioRes = R.raw.forget,
        searchTags = listOf("spy", "team fortress", "tf2")

    ),

    SoundItem(
        title = "Disappointment",
        imageRes = R.drawable.vdp_spy,
        audioRes = R.raw.disappointment,
        searchTags = listOf("spy", "team fortress", "tf2")

    ),

    SoundItem(
        title = "You disgust me",
        imageRes = R.drawable.vdp_spy,
        audioRes = R.raw.disgust,
        searchTags = listOf("spy", "team fortress", "tf2")

    ),

    SoundItem(
        title = "With my Apologies",
        imageRes = R.drawable.vdp_spy,
        audioRes = R.raw.apologies,
        audioExtension = "wav",
        searchTags = listOf("spy", "team fortress", "tf2")

    ),

    SoundItem(
        title = "But of course",
        imageRes = R.drawable.vdp_spy,
        audioRes = R.raw.ofcourse,
        audioExtension = "wav",
        searchTags = listOf("spy", "team fortress", "tf2")

    ),

    SoundItem(
        title = "Such a dear Friend",
        imageRes = R.drawable.vdp_spy,
        audioRes = R.raw.friend,
        audioExtension = "wav",
        searchTags = listOf("spy", "team fortress", "tf2")

    ),

    SoundItem(
        title = "You are an amateur",
        imageRes = R.drawable.vdp_spy,
        audioRes = R.raw.amateur,
        audioExtension = "wav",
        searchTags = listOf("spy", "team fortress", "tf2")

    ),

    SoundItem(
        title = "All in a Day's Work",
        imageRes = R.drawable.vdp_spy,
        audioRes = R.raw.dayswork,
        audioExtension = "wav",
        searchTags = listOf("spy", "team fortress", "tf2")

    ),

    SoundItem(
        title = "Gentlemen",
        imageRes = R.drawable.vdp_spy,
        audioRes = R.raw.gentlemen,
        audioExtension = "wav",
        searchTags = listOf("spy", "team fortress", "tf2")

    )

)

val starcraftSounds = listOf(

    SoundItem(
        title = "Un uomo deve fare...",
        imageRes = R.drawable.vdp_tychus,
        audioRes = R.raw.uomo,
        searchTags = listOf("tychus findlay", "starcraft", "sc")

    ),

    SoundItem(
        title = "Unworthy",
        imageRes = R.drawable.vdp_alarak,
        audioRes = R.raw.unworthy,
        searchTags = listOf("alarak", "starcraft", "sc")

    ),

    SoundItem(
        title = "Inettitudine",
        imageRes = R.drawable.vdp_alarak,
        audioRes = R.raw.inettitudine,
        searchTags = listOf("alarak", "starcraft", "sc")

    )

)

val metalgearSounds = listOf(

    SoundItem(
        title = "My source is that I made it the fuck up",
        imageRes = R.drawable.vdp_armstrong,
        audioRes = R.raw.source,
        searchTags = listOf("metal gear solid", "mgs", "senator armstrong")

    )

)

val animeSounds = listOf(


    SoundItem(
        title = "Yare Yare Daze",
        imageRes = R.drawable.vdp_jotaro,
        audioRes = R.raw.yareyare,
        searchTags = listOf("jojo", "jotaro kujo")

    ),

    SoundItem(
        title = "OraOraOraOra",
        imageRes = R.drawable.vdp_jotaro,
        audioRes = R.raw.oraoraora,
        searchTags = listOf("jojo", "jotaro kujo")

    ),

    SoundItem(
        title = "Arrivederci",
        imageRes = R.drawable.vdp_arrivederci,
        audioRes = R.raw.arrivederci,
        searchTags = listOf("jojo", "bruno bucciarati")

    ),

    SoundItem(
        title = "Za Warudo",
        imageRes = R.drawable.vdp_zawarudo,
        audioRes = R.raw.zawarudo,
        searchTags = listOf("jojo", "dio brando")

    ),

    SoundItem(
        title = "Muda Muda Muda",
        imageRes = R.drawable.vdp_zawarudo,
        audioRes = R.raw.mudamuda,
        searchTags = listOf("jojo", "dio brando")

    ),

    SoundItem(
        title = "United States of Smash",
        imageRes = R.drawable.vdp_allmight,
        audioRes = R.raw.unitedstatesmash,
        searchTags = listOf("all might", "my hero academia", "mha")

    ),

    SoundItem(
        title = "PLUS ULTRA",
        imageRes = R.drawable.vdp_plusultra,
        audioRes = R.raw.plusultra,
        searchTags = listOf("endeavor", "my hero academia", "mha")

    ),

    SoundItem(
        title = "Omae Wa Mou Shindeiru",
        imageRes = R.drawable.vdp_ken,
        audioRes = R.raw.omeawa,
        searchTags = listOf("kenshiro", "ken il guerriero", "hokuto no ken")

    ),

    SoundItem(
        title = "Nani",
        imageRes = R.drawable.vdp_ken,
        audioRes = R.raw.nani,
        searchTags = listOf("kenshiro", "ken il guerriero", "hokuto no ken")

    )

)


val movieSounds = listOf(


    SoundItem(
        title = "Va' alla malora",
        imageRes = R.drawable.vdp_conan,
        audioRes = R.raw.malora,
        searchTags = listOf("conan il barbaro", "arnold schwarzenegger")

    ),

    SoundItem(
        title = "Tu succhi?",
        imageRes = R.drawable.vdp_cazzi,
        audioRes = R.raw.succhi,
        searchTags = listOf("sergente maggiore hartman", "full metal jacket", "stanley kubrick")

    ),

    SoundItem(
        title = "That's what she said",
        imageRes = R.drawable.vdp_scott,
        audioRes = R.raw.thatswhatshesaid,
        searchTags = listOf("michael scott", "steve carell", "the office")

    ),

    SoundItem(
        title = "No God please no",
        imageRes = R.drawable.vdp_scott,
        audioRes = R.raw.noogod,
        searchTags = listOf("michael scott", "steve carell", "the office")

    ),

    SoundItem(
        title = "Parkour",
        imageRes = R.drawable.vdp_scott,
        audioRes = R.raw.parkour,
        searchTags = listOf("michael scott", "steve carell", "the office")

    ),

    SoundItem(
        title = "Not Classy",
        imageRes = R.drawable.vdp_scott,
        audioRes = R.raw.notclassy,
        searchTags = listOf("michael scott", "steve carell", "the office")

    ),

    SoundItem(
        title = "Little Patience for Stupidity",
        imageRes = R.drawable.vdp_kevinmalone,
        audioRes = R.raw.stupidity,
        searchTags = listOf("kevin malone", "the office")

    ),

    SoundItem(
        title = "I am dead inside",
        imageRes = R.drawable.vdp_scott,
        audioRes = R.raw.deadinside,
        searchTags = listOf("michael scott", "steve carell", "the office")

    ),

    SoundItem(
        title = "Effect on Women",
        imageRes = R.drawable.vdp_idris,
        audioRes = R.raw.effectonwomen,
        searchTags = listOf("idris elba", "charles miner", "the office")

    ),

    SoundItem(
        title = "You Gay Bastard",
        imageRes = R.drawable.vdp_jobennett,
        audioRes = R.raw.gaybastard,
        searchTags = listOf("jo bennett", "kathy bates", "the office")

    ),

    SoundItem(
        title = "Quant'è bella la Mafia",
        imageRes = R.drawable.vdp_aggmafia,
        audioRes = R.raw.mafia,
        searchTags = listOf("aldo giovanni e giacomo", "agg", "la leggenda di al john e jack")

    ),

    SoundItem(
        title = "Se ci fosse",
        imageRes = R.drawable.vdp_aggmafia,
        audioRes = R.raw.secifosse,
        searchTags = listOf("aldo giovanni e giacomo", "agg", "la leggenda di al john e jack", "pasta con le sarde")

    ),

    SoundItem(
        title = "Vaffanculo",
        imageRes = R.drawable.vdp_agggamba,
        audioRes = R.raw.vaffanculo,
        searchTags = listOf("aldo giovanni e giacomo", "agg", "tre uomini e una gamba")

    ),

    SoundItem(
        title = "Wyoming",
        imageRes = R.drawable.vdp_agggamba,
        audioRes = R.raw.wyoming,
        searchTags = listOf("aldo giovanni e giacomo", "agg", "tre uomini e una gamba")

    ),

    SoundItem(
        title = "È arrivato il Professorone",
        imageRes = R.drawable.vdp_agggamba,
        audioRes = R.raw.professorone,
        searchTags = listOf("aldo giovanni e giacomo", "agg", "tre uomini e una gamba")

    ),

    SoundItem(
        title = "Niente di serio",
        imageRes = R.drawable.vdp_agggamba,
        audioRes = R.raw.nientediserio,
        searchTags = listOf("aldo giovanni e giacomo", "agg", "tre uomini e una gamba")

    ),

    SoundItem(
        title = "Io ti tiro sotto",
        imageRes = R.drawable.vdp_aggcosmo,
        audioRes = R.raw.titirosotto,
        searchTags = listOf("aldo giovanni e giacomo", "agg", "il cosmo sul comò")

    ),

    SoundItem(
        title = "Tieni giù le mani",
        imageRes = R.drawable.vdp_aggcosmo,
        audioRes = R.raw.giulemani,
        searchTags = listOf("aldo giovanni e giacomo", "agg", "il cosmo sul comò")

    ),

    SoundItem(
        title = "Dove cazzo si esce?",
        imageRes = R.drawable.vdp_aggcosmo,
        audioRes = R.raw.dovesiesce,
        searchTags = listOf("aldo giovanni e giacomo", "agg", "il cosmo sul comò")

    ),

    SoundItem(
        title = "Ti devo anche pagare?",
        imageRes = R.drawable.vdp_aggcosmo,
        audioRes = R.raw.tidevopagare,
        searchTags = listOf("aldo giovanni e giacomo", "agg", "il cosmo sul comò")

    ),

    SoundItem(
        title = "La radiamo al suolo questa merda di casa",
        imageRes = R.drawable.vdp_aggfelice,
        audioRes = R.raw.raderealsuolo,
        searchTags = listOf("aldo giovanni e giacomo", "agg", "chiedimi se sono felice")

    ),

    SoundItem(
        title = "Si sta ribaltando la situazione",
        imageRes = R.drawable.vdp_agganplagghed,
        audioRes = R.raw.situazione,
        searchTags = listOf("aldo giovanni e giacomo", "agg", "anplagghed")

    ),

    SoundItem(
        title = "English, motherfucker",
        imageRes = R.drawable.vdp_pulp,
        audioRes = R.raw.englishmf,
        searchTags = listOf("pulp fiction", "quentin tarantino", "samuel l jackson", "jules winnfield")

),

    SoundItem(
        title = "I'll be back",
        imageRes = R.drawable.vdp_terminator,
        audioRes = R.raw.beback,
        searchTags = listOf("terminator", "arnold schwarzenegger")

    ),

    SoundItem(
        title = "Fuggite, sciocchi",
        imageRes = R.drawable.vdp_gandalf,
        audioRes = R.raw.fuggitesciocchi,
        searchTags = listOf("gandalf", "il signore degli anelli", "lotr")

    ),

    SoundItem(
        title = "Surprise, motherfucker",
        imageRes = R.drawable.vdp_surprise,
        audioRes = R.raw.surprise,
        searchTags = listOf("dexter", "sergente james doakes", "erik king")

    ),

    SoundItem(
        title = "Io gradirei morire",
        imageRes = R.drawable.vdp_boris,
        audioRes = R.raw.gradireimorire,
        searchTags = listOf("boris")

    )

)

val italianSounds = listOf(

    SoundItem(
        title = "Due Milioni di Euro",
        imageRes = R.drawable.vdp_duemilioni,
        audioRes = R.raw.duemilioni,
        searchTags = listOf("alessandro orlando")

    ),

    SoundItem(
        title = "Non dire parolacce",
        imageRes = R.drawable.vdp_enzo,
        audioRes = R.raw.parolacce,
        searchTags = listOf("enzo il matto", "ostia")

    ),

    SoundItem(
        title = "Me sto a senti' male",
        imageRes = R.drawable.vdp_enzo,
        audioRes = R.raw.sentimale,
        searchTags = listOf("enzo il matto", "ostia")

    ),

    SoundItem(
        title = "C'ha detto?",
        imageRes = R.drawable.vdp_enzo,
        audioRes = R.raw.chadetto,
        searchTags = listOf("enzo il matto", "ostia")

    ),

    SoundItem(
        title = "Me ne vado?",
        imageRes = R.drawable.vdp_berlusconi,
        audioRes = R.raw.menevado,
        searchTags = listOf("berlusconi", "cavaliere", "presidente")

    ),

    SoundItem(
        title = "Comunisti",
        imageRes = R.drawable.vdp_berlusconi,
        audioRes = R.raw.comunisti,
        searchTags = listOf("berlusconi", "cavaliere", "presidente")

    ),

    SoundItem(
        title = "Sei bravo a parole",
        imageRes = R.drawable.vdp_moccia,
        audioRes = R.raw.pugni,
        searchTags = listOf("dario moccia", "pugni")

    ),

    SoundItem(
        title = "Pefforza",
        imageRes = R.drawable.vdp_moccia,
        audioRes = R.raw.pefforza,
        searchTags = listOf("dario moccia")

    ),

    SoundItem(
        title = "È l'ora dello Sbusto",
        imageRes = R.drawable.vdp_moccia,
        audioRes = R.raw.sbusto,
        searchTags = listOf("dario moccia", "pacchetto", "pokemon")

    ),

    SoundItem(
        title = "Sivalletto",
        imageRes = R.drawable.vdp_moccia,
        audioRes = R.raw.sivalletto,
        searchTags = listOf("dario moccia", "letto")

    ),

    SoundItem(
        title = "Polizia",
        imageRes = R.drawable.vdp_moccia,
        audioRes = R.raw.polizia,
        searchTags = listOf("dario moccia")

    ),

    SoundItem(
        title = "Lisciami le Mele",
        imageRes = R.drawable.vdp_zeb,
        audioRes = R.raw.mele,
        searchTags = listOf("zeb89", "kenneth caselli")

    ),

    SoundItem(
        title = "Volevi",
        imageRes = R.drawable.vdp_zeb,
        audioRes = R.raw.volevi,
        searchTags = listOf("zeb89", "kenneth caselli", "pensavi")

    ),

    SoundItem(
        title = "Lezzo",
        imageRes = R.drawable.vdp_zeb,
        audioRes = R.raw.lezzo,
        searchTags = listOf("zeb89", "kenneth caselli")

    ),

    SoundItem(
        title = "Paaagah",
        imageRes = R.drawable.vdp_zeb,
        audioRes = R.raw.paga,
        searchTags = listOf("zeb89", "kenneth caselli")

    ),

    SoundItem(
        title = "Non ci sto",
        imageRes = R.drawable.vdp_cicciogamer,
        audioRes = R.raw.provvedimenti,
        searchTags = listOf("cicciogamer89", "provvedimenti")

    ),

    SoundItem(
        title = "Porco...",
        imageRes = R.drawable.vdp_sabaku,
        audioRes = R.raw.sabaku,
        searchTags = listOf("sabaku", "bestemmia")

    ),

    SoundItem(
        title = "Spacciatori",
        imageRes = R.drawable.vdp_salvini,
        audioRes = R.raw.spacciatori,
        searchTags = listOf("matteo salvini", "lista")

    ),

    SoundItem(
        title = "Ti sfido su Fortnite",
        imageRes = R.drawable.vdp_salvini,
        audioRes = R.raw.fortnite,
        searchTags = listOf("matteo salvini", "fortnite")

    ),

    SoundItem(
        title = "Interessante ma non mi interessa",
        imageRes = R.drawable.vdp_salvini,
        audioRes = R.raw.interessante,
        searchTags = listOf("matteo salvini")

    ),

    SoundItem(
        title = "Eresia",
        imageRes = R.drawable.vdp_barbero,
        audioRes = R.raw.eresia,
        searchTags = listOf("alessandro barbero")

    ),

    SoundItem(
        title = "È una cosa che capita",
        imageRes = R.drawable.vdp_barbero,
        audioRes = R.raw.omicidio,
        searchTags = listOf("alessandro barbero", "omicidio")

    ),

    SoundItem(
        title = "La preoccupazione serpeggia",
        imageRes = R.drawable.vdp_barbero,
        audioRes = R.raw.preoccupazione,
        searchTags = listOf("alessandro barbero")

    ),

    SoundItem(
        title = "Andiamo a bruciargli la casa!",
        imageRes = R.drawable.vdp_barbero,
        audioRes = R.raw.bruciarecasa,
        searchTags = listOf("alessandro barbero")

    ),

    SoundItem(
        title = "Lo fanno a pezzettini",
        imageRes = R.drawable.vdp_barbero,
        audioRes = R.raw.pezzettini,
        searchTags = listOf("alessandro barbero")

    ),

    SoundItem(
        title = "I bei tempi dello Squadrismo",
        imageRes = R.drawable.vdp_barbero,
        audioRes = R.raw.squadrismo,
        searchTags = listOf("alessandro barbero")

    ),

    SoundItem(
        title = "Chiamo da Reggio Emilia",
        imageRes = R.drawable.vdp_reggioemilia,
        audioRes = R.raw.reggioemilia,
        searchTags = listOf("bastardi", "assassino", "meridionali")

    ),

    SoundItem(
        title = "Io te posso canta' 'na canzone",
        imageRes = R.drawable.vdp_bombanarchica,
        audioRes = R.raw.canzone,
        searchTags = listOf("bomba anarchica")

    ),

    SoundItem(
        title = "Monella!",
        imageRes = R.drawable.vdp_giuseppesimone,
        audioRes = R.raw.monella,
        searchTags = listOf("giuseppe simone")

    ),

    SoundItem(
        title = "Mai più!",
        imageRes = R.drawable.vdp_zequila,
        audioRes = R.raw.maipiu,
        searchTags = listOf("antonio zequila", "madre")

    ),

    SoundItem(
        title = "Questo rischio c'è",
        imageRes = R.drawable.vdp_conte,
        audioRes = R.raw.rischio,
        searchTags = listOf("giuseppe conte", "dobbiamo dircelo")

    ),

    SoundItem(
        title = "Capra ignorante",
        imageRes = R.drawable.vdp_sgarbi,
        audioRes = R.raw.capraignorante,
        searchTags = listOf("vittorio sgarbi")

    ),

    SoundItem(
        title = "Questa volta no",
        imageRes = R.drawable.vdp_benson,
        audioRes = R.raw.stavoltano,
        searchTags = listOf("richard benson")

    ),

    SoundItem(
        title = "Mi state pigliando per il culo?",
        imageRes = R.drawable.vdp_benson,
        audioRes = R.raw.pigliandoculo,
        searchTags = listOf("richard benson")

    ),

    SoundItem(
        title = "Un pollooooo",
        imageRes = R.drawable.vdp_benson,
        audioRes = R.raw.pollo,
        searchTags = listOf("richard benson")

    ),

    SoundItem(
        title = "Ho la...",
        imageRes = R.drawable.vdp_muniz,
        audioRes = R.raw.nelculo,
        searchTags = listOf("rosario muniz", "figa")

    ),

    SoundItem(
        title = "Mio Padre",
        imageRes = R.drawable.vdp_volpescu,
        audioRes = R.raw.miopadre,
        searchTags = listOf("volpescu")

    ),

    SoundItem(
        title = "Hai succhiato...",
        imageRes = R.drawable.vdp_volpescu,
        audioRes = R.raw.succhiato,
        searchTags = listOf("volpescu", "cazzo")

    ),

    SoundItem(
        title = "Lo faccio sempre all'ultimo try",
        imageRes = R.drawable.vdp_volpescu,
        audioRes = R.raw.ultimotry,
        searchTags = listOf("volpescu")

    ),

    SoundItem(
        title = "Mi dissocio",
        imageRes = R.drawable.vdp_volpescu,
        audioRes = R.raw.dissocio,
        searchTags = listOf("volpescu")

    ),

    SoundItem(
        title = "È finito il tempo delle Mele",
        imageRes = R.drawable.vdp_razdegan,
        audioRes = R.raw.tempomele,
        searchTags = listOf("raz degan", "yotobi", "albakiara", "puttana")

    ),

    SoundItem(
        title = "First Reaction: SHOCK",
        imageRes = R.drawable.vdp_renzi,
        audioRes = R.raw.shock,
        searchTags = listOf("matteo renzi")

    ),

    SoundItem(
        title = "Shish",
        imageRes = R.drawable.vdp_renzi,
        audioRes = R.raw.shish,
        searchTags = listOf("matteo renzi")

    ),

    SoundItem(
        title = "Va bene lo stesso",
        imageRes = R.drawable.vdp_lundini,
        audioRes = R.raw.vabenelostesso,
        searchTags = listOf("valerio lundini")

    ),

    SoundItem(
        title = "Zappo 'a Vigna",
        imageRes = R.drawable.vdp_prattico,
        audioRes = R.raw.zappo,
        searchTags = listOf("enrico pasquale pratticò")

    ),

    SoundItem(
        title = "Sono stato cacciato",
        imageRes = R.drawable.vdp_penitente,
        audioRes = R.raw.cacciato,
        searchTags = listOf("penitente")

    ),

    SoundItem(
        title = "Ho pagato non me fanno entra'",
        imageRes = R.drawable.vdp_hopagato,
        audioRes = R.raw.hopagato,
        searchTags = listOf("penitente")

    ),

    SoundItem(
        title = "Se ni' mondo esistesse un po' di bene",
        imageRes = R.drawable.vdp_pacciani,
        audioRes = R.raw.pacciani,
        searchTags = listOf("pietro pacciani", "mostro di firenze", "fratello")

    ),

    SoundItem(
        title = "Sattoh",
        imageRes = R.drawable.vdp_paniccia,
        audioRes = R.raw.sattoh,
        searchTags = listOf("osvaldo paniccia", "esatto")

    ),

    SoundItem(
        title = "Una cosa seria",
        imageRes = R.drawable.vdp_paniccia,
        audioRes = R.raw.unacosaseria,
        searchTags = listOf("osvaldo paniccia", "l'arte è una cosa seria", "molto seria", "sotto gamba")

    ),

    SoundItem(
        title = "DIOOOO",
        imageRes = R.drawable.vdp_farenz,
        audioRes = R.raw.dio,
        searchTags = listOf("l'angolo di farenz")

    )

)


val internationalSounds = listOf(

    SoundItem(
        title = "Why are you gay?",
        imageRes = R.drawable.vdp_gay,
        audioRes = R.raw.gay

    ),

    SoundItem(
        title = "Faker, what was that?",
        imageRes = R.drawable.vdp_faker,
        audioRes = R.raw.faker,
        searchTags = listOf("faker", "league of legends", "lol")

    ),

    SoundItem(
        title = "Billions and Billions",
        imageRes = R.drawable.vdp_trump,
        audioRes = R.raw.billions,
        searchTags = listOf("donald trump")

    ),

    SoundItem(
        title = "Racism",
        imageRes = R.drawable.vdp_pritzker,
        audioRes = R.raw.racism,
        searchTags = listOf("jay robert pritzker", "sexism", "homophobia", "transphobia", "xenophobia", "antisemitism")

    ),

    SoundItem(
        title = "It's Free Real Estate",
        imageRes = R.drawable.vdp_realestate,
        audioRes = R.raw.realestate

    ),

    SoundItem(
        title = "Noice",
        imageRes = R.drawable.vdp_noice,
        audioRes = R.raw.noice,
        searchTags = listOf("click", "nice")

    ),

    SoundItem(
        title = "John Cena",
        imageRes = R.drawable.vdp_johncena,
        audioRes = R.raw.johncena,
        searchTags = listOf("wrestling", "wwe", "theme", "entrance")

    ),

    SoundItem(
        title = "Emotional Damage",
        imageRes = R.drawable.vdp_stevenhe,
        audioRes = R.raw.emotionaldamage,
        searchTags = listOf("steven he")

    )

)


val vdpSounds = listOf(

    SoundItem(
        title = "Nel Rispetto della Tradizione",
        imageRes = R.drawable.vdp_messere,
        audioRes = R.raw.tradizione,
        searchTags = listOf("messere", "this is life", "villa del presidente", "vdp")

    ),

    SoundItem(
        title = "'Sto Cazzo",
        imageRes = R.drawable.vdp_jollo,
        audioRes = R.raw.stocazzo,
        searchTags = listOf("jollo", "villa del presidente", "vdp")

    ),

    SoundItem(
        title = "Ve lo metterò...",
        imageRes = R.drawable.vdp_catullo,
        audioRes = R.raw.catullo,
        searchTags = listOf("catullo", "villa del presidente", "vdp")

    )

)


val allSounds = (
        allStarsSounds +
                warcraftSounds +
                lolSounds +
                teamfortressSounds +
                starcraftSounds +
                metalgearSounds +
                animeSounds +
                movieSounds +
                italianSounds +
                internationalSounds +
                vdpSounds
        ).distinctBy { it.audioRes }


// --------------------------------------------------
// HOMEPAGE
// --------------------------------------------------


@Composable
fun HomePage(
    onAllStarsClick: () -> Unit,
    onVideoGamesClick: () -> Unit,
    onMovieStarsClick: () -> Unit,
    onClassicMemeClick: () -> Unit,
    onFavoritesClick: () -> Unit
) {

    var searchQuery by remember { mutableStateOf("") }

    val searchResults = allSounds.filter { sound ->
        sound.title.contains(searchQuery, ignoreCase = true) ||
                sound.searchTags.any { tag ->
                    tag.contains(searchQuery, ignoreCase = true)
                }
    }


    val backgroundColor = Color(0xFFC1FF72)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // LOGO
        Image(
            painter = painterResource(id = R.drawable.vdp_logo),
            contentDescription = "VDP Soundboard logo",
            modifier = Modifier
                .fillMaxWidth()
                .height(115.dp),
            contentScale = ContentScale.Fit
        )

        // TITOLO
        Text(
            text = "VDP SOUNDBOARD",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        // SPAZIO TRA TITOLO E PULSANTI
        Spacer(
            modifier = Modifier.height(28.dp)
        )

        // BARRA DI RICERCA
        OutlinedTextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),

            textStyle = TextStyle(
                color = Color.Black,
                fontFamily = soundFontFamily,
                fontSize = 17.sp
            ),

            placeholder = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "⌕",
                        fontSize = 22.sp,
                        color = Color.Gray,
                        modifier = Modifier.offset(y = (-3).dp)
                    )

                    Spacer(
                        modifier = Modifier.width(6.dp)
                    )

                    Text(
                        text = "Cerca un suono...",
                        fontFamily = soundFontFamily,
                        fontSize = 17.sp,
                        color = Color.Gray
                    )
                }
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(
                        onClick = {
                            searchQuery = ""
                        }
                    ) {
                        Text(
                            text = "✕",
                            color = Color.Black,
                            fontSize = 18.sp
                        )
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(4.dp)
        )


        Spacer(
            modifier = Modifier.height(18.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {

            if (searchQuery.isBlank()) {

                // PULSANTE 1
                CategoryButton(
                    imageRes = R.drawable.vdp_allstars,
                    text = "VDP ALL STARS",
                    onClick = onAllStarsClick
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                // PULSANTE 2
                CategoryButton(
                    imageRes = R.drawable.vdp_videogames,
                    text = "VDP VIDEOGAMES",
                    onClick = onVideoGamesClick
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                // PULSANTE 3
                CategoryButton(
                    imageRes = R.drawable.vdp_moviestars,
                    text = "VDP MOVIE STARS",
                    onClick = onMovieStarsClick
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                // PULSANTE 4
                CategoryButton(
                    imageRes = R.drawable.vdp_classicmeme,
                    text = "VDP CLASSIC MEME",
                    onClick = onClassicMemeClick
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                // PULSANTE 5
                CategoryButton(
                    imageRes = R.drawable.vdp_favorites,
                    text = "PREFERITI",
                    onClick = onFavoritesClick
                )

            } else {

                searchResults.forEach { sound ->

                    SoundButton(
                        imageRes = sound.imageRes,
                        title = sound.title,
                        audioRes = sound.audioRes,
                        audioExtension = sound.audioExtension,
                    )

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )
                }
            }

        }


        Text(
            text = "v 3.0.0 | Made by the Messere",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )

        Spacer(
            modifier = Modifier.height(30.dp)
        )
    }
}


// --------------------------------------------------
// PAGINA VDP ALL STARS
// --------------------------------------------------

@Composable
fun AllStarsPage(
    onBackClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFC1FF72))
            .statusBarsPadding()
            .padding(16.dp)
    ) {

        // PULSANTE INDIETRO - FISSO
        TextButton(
            onClick = onBackClick
        ) {
            Text(
                text = "← INDIETRO",
                fontFamily = bungeeFontFamily,
                fontSize = 18.sp,
                color = Color.Black
            )
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        // PARTE SCORREVOLE
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {

            // TITOLO
            Text(
                text = "VDP ALL STARS",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontFamily = bungeeFontFamily,
                fontSize = 28.sp,
                color = Color.Black,
                style = TextStyle(
                    drawStyle = Stroke(
                        width = 7f,
                        join = StrokeJoin.Round
                    )
                )
            )

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            // LISTA SUONI
            allStarsSounds.forEach { sound ->

                SoundButton(
                    imageRes = sound.imageRes,
                    title = sound.title,
                    audioRes = sound.audioRes,
                    audioExtension = sound.audioExtension
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )
            }
        }
    }
}

@Composable
fun VideoGamesPage(
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFC1FF72))
            .statusBarsPadding()
            .padding(16.dp)
    ) {

        // PULSANTE INDIETRO - FISSO
        TextButton(
            onClick = onBackClick
        ) {
            Text(
                text = "← INDIETRO",
                fontFamily = bungeeFontFamily,
                fontSize = 18.sp,
                color = Color.Black
            )
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        // PARTE SCORREVOLE
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {

            // TITOLO
            Text(
                text = "VDP VIDEOGAMES",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontFamily = bungeeFontFamily,
                fontSize = 28.sp,
                color = Color.Black,
                style = TextStyle(
                    drawStyle = Stroke(
                        width = 7f,
                        join = StrokeJoin.Round
                    )
                )
            )

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            // SEZIONE VIDEOGAME

            GameSection(
                title = "WORLD OF WARCRAFT",
                sounds = warcraftSounds
            )

            GameSection(
                title = "LEAGUE OF LEGENDS",
                sounds = lolSounds
            )

            GameSection(
                title = "TEAM FORTRESS 2",
                sounds = teamfortressSounds
            )

            GameSection(
                title = "STARCRAFT",
                sounds = starcraftSounds
            )

            GameSection(
                title = "METAL GEAR",
                sounds = metalgearSounds
            )
        }
    }
}

@Composable
fun GameSection(
    title: String,
    sounds: List<SoundItem>
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        // TITOLO DELLA SEZIONE
        TextButton(
            onClick = {
                expanded = !expanded
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = if (expanded) "▼ $title" else "▶ $title",
                modifier = Modifier.fillMaxWidth(),
                fontFamily = soundFontFamily,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Start
            )
        }

        // CONTENUTO DELLA SEZIONE
        if (expanded) {

            sounds.forEach { sound ->

                SoundButton(
                    imageRes = sound.imageRes,
                    title = sound.title,
                    audioRes = sound.audioRes,
                    audioExtension = sound.audioExtension
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )
            }
        }
    }
}

@Composable
fun MovieStarsPage(
    onBackClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFC1FF72))
            .statusBarsPadding()
            .padding(16.dp)
    ) {

        // PULSANTE INDIETRO - FISSO
        TextButton(
            onClick = onBackClick
        ) {
            Text(
                text = "← INDIETRO",
                fontFamily = bungeeFontFamily,
                fontSize = 18.sp,
                color = Color.Black
            )
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        // PARTE SCORREVOLE
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {

            // TITOLO
            Text(
                text = "VDP MOVIE STARS",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontFamily = bungeeFontFamily,
                fontSize = 28.sp,
                color = Color.Black,
                style = TextStyle(
                    drawStyle = Stroke(
                        width = 7f,
                        join = StrokeJoin.Round
                    )
                )
            )

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            // SEZIONE MOVIE

            GameSection(
                title = "ANIME",
                sounds = animeSounds
            )

            GameSection(
                title = "MOVIES",
                sounds = movieSounds
            )
        }
    }
}


@Composable
fun ClassicMemePage(
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFC1FF72))
            .statusBarsPadding()
            .padding(16.dp)
    ) {

        // PULSANTE INDIETRO - FISSO
        TextButton(
            onClick = onBackClick
        ) {
            Text(
                text = "← INDIETRO",
                fontFamily = bungeeFontFamily,
                fontSize = 18.sp,
                color = Color.Black
            )
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        // PARTE SCORREVOLE
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {

            // TITOLO
            Text(
                text = "VDP CLASSIC MEME",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontFamily = bungeeFontFamily,
                fontSize = 28.sp,
                color = Color.Black,
                style = TextStyle(
                    drawStyle = Stroke(
                        width = 7f,
                        join = StrokeJoin.Round
                    )
                )
            )

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            // SEZIONE MEME CLASSICI

            GameSection(
                title = "ITALIAN MEMES",
                sounds = italianSounds
            )

            GameSection(
                title = "INTERNATIONAL MEMES",
                sounds = internationalSounds
            )

            GameSection(
                title = "VDP MEMES",
                sounds = vdpSounds
            )
        }
    }
}


@Composable
fun FavoritesPage(
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFC1FF72))
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        TextButton(onClick = onBackClick) {
            Text(
                text = "← INDIETRO",
                fontFamily = bungeeFontFamily,
                fontSize = 18.sp,
                color = Color.Black
            )
        }

        Spacer(
            modifier = Modifier.height(30.dp)
        )

        if (favoriteAudioRes.isEmpty()) {

            Text(
                text = "Nessun Preferito",
                fontFamily = soundFontFamily,
                fontSize = 22.sp,
                fontStyle = FontStyle.Italic,
                color = Color.Black,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

        } else {

            allSounds
                .filter { sound ->
                    favoriteAudioRes.contains(sound.audioRes)
                }
                .forEach { sound ->

                    SoundButton(
                        imageRes = sound.imageRes,
                        title = sound.title,
                        audioRes = sound.audioRes,
                        audioExtension = sound.audioExtension
                    )

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )
                }
        }
    }
}


// --------------------------------------------------
// PULSANTI DELLE CATEGORIE
// --------------------------------------------------

@Composable
fun CategoryButton(
    imageRes: Int,
    text: String,
    onClick: () -> Unit = {}
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .border(
                width = 2.dp,
                color = Color.Black,
                shape = RoundedCornerShape(4.dp)
            )
            .clickable {
                onClick()
            }
    ) {

        // IMMAGINE DEL PULSANTE
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = text,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        // TESTO SOPRA L'IMMAGINE
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            // OUTLINE NERO
            Text(
                text = text,
                fontFamily = bungeeFontFamily,
                fontSize = 29.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                style = TextStyle(
                    drawStyle = Stroke(
                        width = 8f,
                        join = StrokeJoin.Round,
                        cap = StrokeCap.Round
                    )
                )
            )

// TESTO BIANCO
            Text(
                text = text,
                fontFamily = bungeeFontFamily,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}


//-------------------------
// FUNZIONE CONDIVISIONE
//-------------------------


fun shareAudio(
    context: android.content.Context,
    audioRes: Int,
    audioExtension: String
) {
    val resources = context.resources

    // Nome del file originale, ad esempio:
    // R.raw.bonk -> "bonk"
    // R.raw.forget -> "forget"
    val originalName = resources.getResourceEntryName(audioRes)

    // Cerchiamo automaticamente:
    // bonk_ogg
    // forget_ogg
    // gloria_ogg
    // ecc.
    val oggRes = resources.getIdentifier(
        "${originalName}_ogg",
        "raw",
        context.packageName
    )

    if (oggRes == 0) {
        android.widget.Toast.makeText(
            context,
            "Versione OGG non trovata per: $originalName",
            android.widget.Toast.LENGTH_LONG
        ).show()

        return
    }

    val sharedAudioDir = File(
        context.cacheDir,
        "shared_audio"
    )

    if (!sharedAudioDir.exists()) {
        sharedAudioDir.mkdirs()
    }

    val audioFile = File(
        sharedAudioDir,
        "$originalName.ogg"
    )

    // Copia il vero OGG/Opus nella cache
    resources.openRawResource(oggRes).use { input ->
        audioFile.outputStream().use { output ->
            input.copyTo(output)
        }
    }

    val uri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider",
        audioFile
    )

    val shareIntent = Intent(Intent.ACTION_SEND).apply {

        type = "audio/ogg"

        putExtra(
            Intent.EXTRA_STREAM,
            uri
        )

        addFlags(
            Intent.FLAG_GRANT_READ_URI_PERMISSION
        )
    }

    context.startActivity(
        Intent.createChooser(
            shareIntent,
            "Condividi audio"
        )
    )
}




// --------------------------------------------------
// PULSANTE DEI SINGOLI SUONI
// --------------------------------------------------

@Composable
fun SoundButton(
    imageRes: Int,
    title: String,
    audioRes: Int,
    audioExtension: String
) {
    val context = LocalContext.current
    val isFavorite = favoriteAudioRes.contains(audioRes)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clickable {

                val mediaPlayer = MediaPlayer.create(
                    context,
                    audioRes
                )

                mediaPlayer?.setOnCompletionListener {
                    it.release()
                }

                mediaPlayer?.start()
            }
    ) {

        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            // IMMAGINE DEL PULSANTE
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )

            // TESTO
            Text(
                text = title,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 85.dp,
                        end = 50.dp
                    )
                    .align(Alignment.Center),
                fontFamily = soundFontFamily,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center
            )

            // PULSANTE PREFERITO
            Box(
                modifier = Modifier
                    .width(30.dp)
                    .height(60.dp)
                    .align(Alignment.CenterEnd)
                    .offset(x = (-35).dp, y = (-2).dp)
                    .clickable {
                        favoriteAudioRes = if (isFavorite) {
                            favoriteAudioRes - audioRes
                        } else {
                            favoriteAudioRes + audioRes
                        }

                        context
                            .getSharedPreferences("favorites", android.content.Context.MODE_PRIVATE)
                            .edit()
                            .putStringSet(
                                "favorite_audio_res",
                                favoriteAudioRes.map { it.toString() }.toSet()
                            )
                            .apply()
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (isFavorite) "★" else "☆",
                    fontSize = 20.sp,
                    color = Color.Black
                )
            }

            // PULSANTE CONDIVIDI
            Box(
                modifier = Modifier
                    .width(45.dp)
                    .height(60.dp)
                    .align(Alignment.CenterEnd)
                    .clickable {
                        shareAudio(
                            context,
                            audioRes,
                            audioExtension
                        )
                    },
                contentAlignment = Alignment.Center
            ) {

                Canvas(
                    modifier = Modifier.fillMaxSize()
                ) {

                    val cx = size.width / 2f
                    val cy = size.height / 2f

                    // QUADRATINO
                    val boxPath = androidx.compose.ui.graphics.Path()

                    boxPath.moveTo(cx - 5f, cy - 14f)
                    boxPath.lineTo(cx - 11f, cy - 14f)

                    boxPath.lineTo(cx - 11f, cy + 9f)

                    boxPath.lineTo(cx - 5f, cy + 14f)

                    boxPath.lineTo(cx + 9f, cy + 14f)

                    boxPath.lineTo(cx + 14f, cy + 9f)

                    boxPath.lineTo(cx + 14f, cy + 3f)

                    drawPath(
                        path = boxPath,
                        color = Color.Black,
                        style = androidx.compose.ui.graphics.drawscope.Stroke(
                            width = 3f,
                            cap = StrokeCap.Round,
                            join = StrokeJoin.Round
                        )
                    )

                    // FRECCIA ↗
                    val arrowPath = androidx.compose.ui.graphics.Path()

                    // Asta diagonale
                    arrowPath.moveTo(cx - 4f, cy + 7f)
                    arrowPath.lineTo(cx + 12f, cy - 9f)

                    // Punta superiore
                    arrowPath.moveTo(cx + 12f, cy - 9f)
                    arrowPath.lineTo(cx + 4f, cy - 9f)

                    // Punta destra
                    arrowPath.moveTo(cx + 12f, cy - 9f)
                    arrowPath.lineTo(cx + 12f, cy - 1f)

                    drawPath(
                        path = arrowPath,
                        color = Color.Black,
                        style = androidx.compose.ui.graphics.drawscope.Stroke(
                            width = 3f,
                            cap = StrokeCap.Round,
                            join = StrokeJoin.Round
                        )
                    )
                }
            }
        }
    }
}
