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
import android.net.Uri
import java.io.FileOutputStream
import androidx.compose.material3.Icon
import androidx.compose.foundation.Canvas


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

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
                        }
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
    val audioExtension: String = "mp3"
)

val allStarsSounds = listOf(

    SoundItem(
        title = "Due Milioni di Euro",
        imageRes = R.drawable.vdp_duemilioni,
        audioRes = R.raw.duemilioni
    ),

    SoundItem(
        title = "Force of Nature",
        imageRes = R.drawable.vdp_scout,
        audioRes = R.raw.forcenature
    ),

    SoundItem(
        title = "Gloria",
        imageRes = R.drawable.vdp_fordring,
        audioRes = R.raw.gloria

    ),

    SoundItem(
        title = "Sta entrando un Ne-",
        imageRes = R.drawable.vdp_neg,
        audioRes = R.raw.neg

    ),

    SoundItem(
        title = "Did you forget?",
        imageRes = R.drawable.vdp_spy,
        audioRes = R.raw.forget

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
        audioRes = R.raw.disappointment

    ),

    SoundItem(
        title = "Non dire parolacce",
        imageRes = R.drawable.vdp_polizia,
        audioRes = R.raw.parolacce

    ),

    SoundItem(
        title = "È notte fonda",
        imageRes = R.drawable.vdp_messere,
        audioRes = R.raw.nottefonda

    ),

    SoundItem(
        title = "Gimme Money",
        imageRes = R.drawable.vdp_bobo,
        audioRes = R.raw.gimmemoney

    ),

    SoundItem(
        title = "A coward... PUAH!",
        imageRes = R.drawable.vdp_garrosh,
        audioRes = R.raw.coward

    ),

    SoundItem(
        title = "You cannot kill hope",
        imageRes = R.drawable.vdp_saurfang,
        audioRes = R.raw.killhope

    ),

    SoundItem(
        title = "Quest complete",
        imageRes = R.drawable.vdp_quest,
        audioRes = R.raw.quest

    ),

    SoundItem(
        title = "Ridi Pagliaccio",
        imageRes = R.drawable.vdp_pagliaccio,
        audioRes = R.raw.ridipagliaccio

    ),

    SoundItem(
        title = "Arrivederci",
        imageRes = R.drawable.vdp_arrivederci,
        audioRes = R.raw.arrivederci

    ),

    SoundItem(
        title = "Devi morire",
        imageRes = R.drawable.vdp_messere,
        audioRes = R.raw.devimorire

    ),

    SoundItem(
        title = "You disgust me",
        imageRes = R.drawable.vdp_spy,
        audioRes = R.raw.disgust

    ),

    SoundItem(
        title = "Brutta Gente",
        imageRes = R.drawable.vdp_pyke,
        audioRes = R.raw.bruttagente

    ),

    SoundItem(
        title = "PLUS ULTRA",
        imageRes = R.drawable.vdp_plusultra,
        audioRes = R.raw.plusultra

    ),

    SoundItem(
        title = "We fight to the end",
        imageRes = R.drawable.vdp_fordring,
        audioRes = R.raw.fight

    ),

    SoundItem(
        title = "Me ne vado?",
        imageRes = R.drawable.vdp_berlusconi,
        audioRes = R.raw.menevado

    ),

    SoundItem(
        title = "Nessuna pietà",
        imageRes = R.drawable.vdp_pantheon,
        audioRes = R.raw.nessunapieta

    ),

    SoundItem(
        title = "Watch your clever mouth",
        imageRes = R.drawable.vdp_garrosh,
        audioRes = R.raw.clevermouth

    ),

    SoundItem(
        title = "Va' alla malora",
        imageRes = R.drawable.vdp_conan,
        audioRes = R.raw.malora

    ),

    SoundItem(
        title = "Sei bravo a parole",
        imageRes = R.drawable.vdp_moccia,
        audioRes = R.raw.pugni

    ),

    SoundItem(
        title = "Tu succhi?",
        imageRes = R.drawable.vdp_cazzi,
        audioRes = R.raw.succhi

    ),

    SoundItem(
        title = "It's the Capricorn",
        imageRes = R.drawable.vdp_capricorn,
        audioRes = R.raw.capricorn

    ),

    SoundItem(
        title = "El Racista",
        imageRes = R.drawable.vdp_elracista,
        audioRes = R.raw.elracista

    ),

    SoundItem(
        title = "Za Warudo",
        imageRes = R.drawable.vdp_zawarudo,
        audioRes = R.raw.zawarudo

    ),

    SoundItem(
        title = "Figlio del Fordragone",
        imageRes = R.drawable.vdp_carino,
        audioRes = R.raw.carino

    ),

    SoundItem(
        title = "MRGRLGRL",
        imageRes = R.drawable.vdp_murloc,
        audioRes = R.raw.murloc

    ),

    SoundItem(
        title = "I have been called",
        imageRes = R.drawable.vdp_reinhardt,
        audioRes = R.raw.always

    ),

    SoundItem(
        title = "Relaxing Jazz",
        imageRes = R.drawable.vdp_jazz,
        audioRes = R.raw.jazz

    ),

    SoundItem(
        title = "Keep cryin' baby",
        imageRes = R.drawable.vdp_heavy,
        audioRes = R.raw.keepcrying

    ),

    SoundItem(
        title = "Baby Shark",
        imageRes = R.drawable.vdp_babyshark,
        audioRes = R.raw.babyshark

    ),

    SoundItem(
        title = "Per favore non piangere",
        imageRes = R.drawable.vdp_nonpiangere,
        audioRes = R.raw.nonpiangere

    ),

    SoundItem(
        title = "Unworthy",
        imageRes = R.drawable.vdp_alarak,
        audioRes = R.raw.unworthy

    ),

    SoundItem(
        title = "Objection!",
        imageRes = R.drawable.vdp_objection,
        audioRes = R.raw.objection

    ),

    SoundItem(
        title = "Lisciami le Mele",
        imageRes = R.drawable.vdp_zeb,
        audioRes = R.raw.mele

    ),

    SoundItem(
        title = "Put your Faith in the Light",
        imageRes = R.drawable.vdp_fordring,
        audioRes = R.raw.light

    ),

    SoundItem(
        title = "Non ci sto",
        imageRes = R.drawable.vdp_cicciogamer,
        audioRes = R.raw.provvedimenti

    ),

    SoundItem(
        title = "Quant'è bella la Mafia",
        imageRes = R.drawable.vdp_mafia,
        audioRes = R.raw.mafia

    ),

    SoundItem(
        title = "Porco...",
        imageRes = R.drawable.vdp_sabaku,
        audioRes = R.raw.sabaku

    ),

    SoundItem(
        title = "AAAAAAAAAAH",
        imageRes = R.drawable.vdp_goblin,
        audioRes = R.raw.goblin

    ),

    SoundItem(
        title = "Inettitudine",
        imageRes = R.drawable.vdp_alarak,
        audioRes = R.raw.inettitudine

    ),

    )

val warcraftSounds = listOf(

    SoundItem(
        title = "Gloria",
        imageRes = R.drawable.vdp_fordring,
        audioRes = R.raw.gloria
    ),

    SoundItem(
        title = "We fight to the end",
        imageRes = R.drawable.vdp_fordring,
        audioRes = R.raw.fight
    ),

    SoundItem(
        title = "Put your Faith in the Light",
        imageRes = R.drawable.vdp_fordring,
        audioRes = R.raw.light
    ),

    SoundItem(
        title = "MRGRLGRL",
        imageRes = R.drawable.vdp_murloc,
        audioRes = R.raw.murloc

    ),

    SoundItem(
        title = "Watch your clever mouth",
        imageRes = R.drawable.vdp_garrosh,
        audioRes = R.raw.clevermouth

    ),

    SoundItem(
        title = "A coward... PUAH!",
        imageRes = R.drawable.vdp_garrosh,
        audioRes = R.raw.coward

    ),

    SoundItem(
        title = "You cannot kill hope",
        imageRes = R.drawable.vdp_saurfang,
        audioRes = R.raw.killhope

    ),

    SoundItem(
        title = "Quest complete",
        imageRes = R.drawable.vdp_quest,
        audioRes = R.raw.quest

    ),

    SoundItem(
        title = "You are not prepared",
        imageRes = R.drawable.vdp_illidan,
        audioRes = R.raw.prepared

    ),

    SoundItem(
        title = "Frostmourne hungers",
        imageRes = R.drawable.vdp_lichking,
        audioRes = R.raw.frostmourne

    ),

    SoundItem(
        title = "I AM THE CATACLYSM",
        imageRes = R.drawable.vdp_deathwing,
        audioRes = R.raw.cataclysm

    ),

    SoundItem(
        title = "Time is money, friend",
        imageRes = R.drawable.vdp_wowgoblin,
        audioRes = R.raw.timeismoney

    )

)

val lolSounds = listOf(

    SoundItem(
        title = "Nessuna pietà",
        imageRes = R.drawable.vdp_pantheon,
        audioRes = R.raw.nessunapieta
    ),

    SoundItem(
        title = "Brutta Gente",
        imageRes = R.drawable.vdp_pyke,
        audioRes = R.raw.bruttagente
    ),

    SoundItem(
        title = "Se i Cervelli fossero Proiettili",
        imageRes = R.drawable.vdp_tahmkench,
        audioRes = R.raw.cervelli
    ),

    SoundItem(
        title = "Buongiorno Fiorellino",
        imageRes = R.drawable.vdp_mundo,
        audioRes = R.raw.fiorellino
    ),

    SoundItem(
        title = "Tutututututu",
        imageRes = R.drawable.vdp_twitch,
        audioRes = R.raw.tututu
    ),

    SoundItem(
        title = "Ero nascosto",
        imageRes = R.drawable.vdp_twitch,
        audioRes = R.raw.nascosto
    )

)


val teamfortressSounds = listOf(

    SoundItem(
        title = "Force of Nature",
        imageRes = R.drawable.vdp_scout,
        audioRes = R.raw.forcenature
    ),

    SoundItem(
        title = "BONK!",
        imageRes = R.drawable.vdp_scout,
        audioRes = R.raw.bonk,
        audioExtension = "wav"
    ),

    SoundItem(
        title = "This sucks on ice!",
        imageRes = R.drawable.vdp_scout,
        audioRes = R.raw.sucksice,
        audioExtension = "wav"
    ),

    SoundItem(
        title = "Un-freakin'-touchable",
        imageRes = R.drawable.vdp_scout,
        audioRes = R.raw.untouchable,
        audioExtension = "wav"
    ),

    SoundItem(
        title = "Paying attention",
        imageRes = R.drawable.vdp_scout,
        audioRes = R.raw.attention,
        audioExtension = "wav"
    ),

    SoundItem(
        title = "Maggots",
        imageRes = R.drawable.vdp_soldier,
        audioRes = R.raw.maggots,
        audioExtension = "wav"
    ),

    SoundItem(
        title = "Maybe even the best",
        imageRes = R.drawable.vdp_soldier,
        audioRes = R.raw.maybethebest,
        audioExtension = "wav"
    ),

    SoundItem(
        title = "You deserve a Medal",
        imageRes = R.drawable.vdp_soldier,
        audioRes = R.raw.medal,
        audioExtension = "wav"
    ),

    SoundItem(
        title = "Glue you back together",
        imageRes = R.drawable.vdp_demo,
        audioRes = R.raw.glueyou,
        audioExtension = "wav"
    ),

    SoundItem(
        title = "Only one",
        imageRes = R.drawable.vdp_demo,
        audioRes = R.raw.onlyone,
        audioExtension = "wav"
    ),

    SoundItem(
        title = "Burn in Hell",
        imageRes = R.drawable.vdp_demo,
        audioRes = R.raw.burninhell,
        audioExtension = "wav"
    ),

    SoundItem(
        title = "Cheers, mate",
        imageRes = R.drawable.vdp_demo,
        audioRes = R.raw.cheers,
        audioExtension = "wav"
    ),

    SoundItem(
        title = "Keep cryin' baby",
        imageRes = R.drawable.vdp_heavy,
        audioRes = R.raw.keepcrying,
        audioExtension = "wav"

    ),

    SoundItem(
        title = "Cry some more",
        imageRes = R.drawable.vdp_heavy,
        audioRes = R.raw.crymore,
        audioExtension = "wav"

    ),

    SoundItem(
        title = "Ya-ta-da-Kaboom",
        imageRes = R.drawable.vdp_heavy,
        audioRes = R.raw.kaboom,
        audioExtension = "wav"

    ),

    SoundItem(
        title = "You are dead, not big surprise",
        imageRes = R.drawable.vdp_heavy,
        audioRes = R.raw.nosurprise,
        audioExtension = "wav"

    ),

    SoundItem(
        title = "Never make me angry",
        imageRes = R.drawable.vdp_heavy,
        audioRes = R.raw.angry,
        audioExtension = "wav"

    ),

    SoundItem(
        title = "How could this happen?",
        imageRes = R.drawable.vdp_heavy,
        audioRes = R.raw.howhappen,
        audioExtension = "wav"

    ),

    SoundItem(
        title = "Thanks, Mister",
        imageRes = R.drawable.vdp_engi,
        audioRes = R.raw.thanks,
        audioExtension = "wav"

    ),

    SoundItem(
        title = "Another satisfied Customer",
        imageRes = R.drawable.vdp_engi,
        audioRes = R.raw.customer,
        audioExtension = "wav"

    ),

    SoundItem(
        title = "A little less Gun",
        imageRes = R.drawable.vdp_engi,
        audioRes = R.raw.lessgun,
        audioExtension = "wav"

    ),

    SoundItem(
        title = "Drunk on the Battlefield",
        imageRes = R.drawable.vdp_engi,
        audioRes = R.raw.drunk,
        audioExtension = "wav"

    ),

    SoundItem(
        title = "That just ain't right",
        imageRes = R.drawable.vdp_engi,
        audioRes = R.raw.aintright,
        audioExtension = "wav"

    ),

    SoundItem(
        title = "Schweinhunds",
        imageRes = R.drawable.vdp_medic,
        audioRes = R.raw.schweinhunds,
        audioExtension = "wav"

    ),

    SoundItem(
        title = "Gesundheit",
        imageRes = R.drawable.vdp_medic,
        audioRes = R.raw.gesundheit,
        audioExtension = "wav"

    ),

    SoundItem(
        title = "I am melting",
        imageRes = R.drawable.vdp_medic,
        audioRes = R.raw.melting,
        audioExtension = "wav"

    ),

    SoundItem(
        title = "Auf Wiedersehen",
        imageRes = R.drawable.vdp_medic,
        audioRes = R.raw.aufwiedersehen,
        audioExtension = "wav"

    ),

    SoundItem(
        title = "Piss off, you mongrels",
        imageRes = R.drawable.vdp_sniper,
        audioRes = R.raw.pissoff,
        audioExtension = "wav"

    ),

    SoundItem(
        title = "G'day!",
        imageRes = R.drawable.vdp_sniper,
        audioRes = R.raw.gday,
        audioExtension = "wav"

    ),

    SoundItem(
        title = "Appreciate it, mate",
        imageRes = R.drawable.vdp_sniper,
        audioRes = R.raw.appreciate,
        audioExtension = "wav"

    ),

    SoundItem(
        title = "Outta bed",
        imageRes = R.drawable.vdp_sniper,
        audioRes = R.raw.outtabed,
        audioExtension = "wav"

    ),

    SoundItem(
        title = "Bloody Heel, you're awful",
        imageRes = R.drawable.vdp_sniper,
        audioRes = R.raw.awful,
        audioExtension = "wav"

    ),

    SoundItem(
        title = "See you in five minutes",
        imageRes = R.drawable.vdp_sniper,
        audioRes = R.raw.fiveminutes,
        audioExtension = "wav"

    ),

    SoundItem(
        title = "Skill always beats Luck",
        imageRes = R.drawable.vdp_sniper,
        audioRes = R.raw.skill,
        audioExtension = "wav"

    ),

    SoundItem(
        title = "Did you forget?",
        imageRes = R.drawable.vdp_spy,
        audioRes = R.raw.forget

    ),

    SoundItem(
        title = "Disappointment",
        imageRes = R.drawable.vdp_spy,
        audioRes = R.raw.disappointment

    ),

    SoundItem(
        title = "You disgust me",
        imageRes = R.drawable.vdp_spy,
        audioRes = R.raw.disgust

    ),

    SoundItem(
        title = "With my Apologies",
        imageRes = R.drawable.vdp_spy,
        audioRes = R.raw.apologies,
        audioExtension = "wav"

    ),

    SoundItem(
        title = "Such a dear Friend",
        imageRes = R.drawable.vdp_spy,
        audioRes = R.raw.friend,
        audioExtension = "wav"

    ),

    SoundItem(
        title = "You are an amateur",
        imageRes = R.drawable.vdp_spy,
        audioRes = R.raw.amateur,
        audioExtension = "wav"

    ),

    SoundItem(
        title = "All in a Day's Work",
        imageRes = R.drawable.vdp_spy,
        audioRes = R.raw.dayswork,
        audioExtension = "wav"

    ),

    SoundItem(
        title = "Gentlemen",
        imageRes = R.drawable.vdp_spy,
        audioRes = R.raw.gentlemen,
        audioExtension = "wav"

    )

)

val animeSounds = listOf(


    SoundItem(
        title = "Yare Yare Daze",
        imageRes = R.drawable.vdp_jotaro,
        audioRes = R.raw.yareyare

    ),

    SoundItem(
        title = "OraOraOraOra",
        imageRes = R.drawable.vdp_jotaro,
        audioRes = R.raw.oraoraora

    ),

    SoundItem(
        title = "Arrivederci",
        imageRes = R.drawable.vdp_arrivederci,
        audioRes = R.raw.arrivederci

    ),

    SoundItem(
        title = "Za Warudo",
        imageRes = R.drawable.vdp_zawarudo,
        audioRes = R.raw.zawarudo

    ),

    SoundItem(
        title = "Muda Muda Muda",
        imageRes = R.drawable.vdp_zawarudo,
        audioRes = R.raw.mudamuda

    ),

    SoundItem(
        title = "United States of Smash",
        imageRes = R.drawable.vdp_allmight,
        audioRes = R.raw.unitedstatesmash

    ),

    SoundItem(
        title = "PLUS ULTRA",
        imageRes = R.drawable.vdp_plusultra,
        audioRes = R.raw.plusultra

    ),

    SoundItem(
        title = "Omae Wa Mou Shindeiru",
        imageRes = R.drawable.vdp_ken,
        audioRes = R.raw.omeawa

    ),

    SoundItem(
        title = "Nani",
        imageRes = R.drawable.vdp_ken,
        audioRes = R.raw.nani

    )

)


val movieSounds = listOf(


    SoundItem(
        title = "Va' alla malora",
        imageRes = R.drawable.vdp_conan,
        audioRes = R.raw.malora

    ),

    SoundItem(
        title = "Tu succhi?",
        imageRes = R.drawable.vdp_cazzi,
        audioRes = R.raw.succhi

    ),

    SoundItem(
        title = "That's what she said",
        imageRes = R.drawable.vdp_scott,
        audioRes = R.raw.thatswhatshesaid

    ),

    SoundItem(
        title = "No God please no",
        imageRes = R.drawable.vdp_scott,
        audioRes = R.raw.noogod

    ),

    SoundItem(
        title = "Parkour",
        imageRes = R.drawable.vdp_scott,
        audioRes = R.raw.parkour

    ),

    SoundItem(
        title = "Quant'è bella la Mafia",
        imageRes = R.drawable.vdp_mafia,
        audioRes = R.raw.mafia

    ),

    SoundItem(
        title = "English, motherfucker",
        imageRes = R.drawable.vdp_pulp,
        audioRes = R.raw.englishmf

    ),

    SoundItem(
        title = "I'll be back",
        imageRes = R.drawable.vdp_terminator,
        audioRes = R.raw.beback

    ),

    SoundItem(
        title = "Fuggite, sciocchi",
        imageRes = R.drawable.vdp_gandalf,
        audioRes = R.raw.fuggitesciocchi

    )

)

val italianSounds = listOf(

    SoundItem(
        title = "Due Milioni di Euro",
        imageRes = R.drawable.vdp_duemilioni,
        audioRes = R.raw.duemilioni
    ),

    SoundItem(
        title = "Non dire parolacce",
        imageRes = R.drawable.vdp_polizia,
        audioRes = R.raw.parolacce

    ),

    SoundItem(
        title = "Me ne vado?",
        imageRes = R.drawable.vdp_berlusconi,
        audioRes = R.raw.menevado

    ),

    SoundItem(
        title = "Comunisti",
        imageRes = R.drawable.vdp_berlusconi,
        audioRes = R.raw.comunisti

    ),

    SoundItem(
        title = "Sei bravo a parole",
        imageRes = R.drawable.vdp_moccia,
        audioRes = R.raw.pugni

    ),

    SoundItem(
        title = "Pefforza",
        imageRes = R.drawable.vdp_moccia,
        audioRes = R.raw.pefforza

    ),

    SoundItem(
        title = "È l'ora dello Sbusto",
        imageRes = R.drawable.vdp_moccia,
        audioRes = R.raw.sbusto

    ),

    SoundItem(
        title = "Lisciami le Mele",
        imageRes = R.drawable.vdp_zeb,
        audioRes = R.raw.mele

    ),

    SoundItem(
        title = "Volevi",
        imageRes = R.drawable.vdp_zeb,
        audioRes = R.raw.volevi

    ),

    SoundItem(
        title = "Lezzo",
        imageRes = R.drawable.vdp_zeb,
        audioRes = R.raw.lezzo

    ),

    SoundItem(
        title = "Non ci sto",
        imageRes = R.drawable.vdp_cicciogamer,
        audioRes = R.raw.provvedimenti

    ),

    SoundItem(
        title = "Porco...",
        imageRes = R.drawable.vdp_sabaku,
        audioRes = R.raw.sabaku

    ),

    SoundItem(
        title = "Spacciatori",
        imageRes = R.drawable.vdp_salvini,
        audioRes = R.raw.spacciatori

    ),

    SoundItem(
        title = "Eresia",
        imageRes = R.drawable.vdp_barbero,
        audioRes = R.raw.eresia

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
        audioRes = R.raw.faker

    ),

    SoundItem(
        title = "Billions and Billions",
        imageRes = R.drawable.vdp_trump,
        audioRes = R.raw.billions

    ),

    SoundItem(
        title = "Racism",
        imageRes = R.drawable.vdp_pritzker,
        audioRes = R.raw.racism

    )

)

// --------------------------------------------------
// HOMEPAGE
// --------------------------------------------------

@Composable
fun HomePage(
    onAllStarsClick: () -> Unit,
    onVideoGamesClick: () -> Unit,
    onMovieStarsClick: () -> Unit,
    onClassicMemeClick: () -> Unit
) {

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
            modifier = Modifier.weight(1f)
        )


        Text(
            text = "v 1.0 | Made by the Messere",
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
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        // PULSANTE INDIETRO
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

@Composable
fun VideoGamesPage(
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

        // PULSANTE INDIETRO
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
        );

        GameSection(
            title = "LEAGUE OF LEGENDS",
            sounds = lolSounds
        );

        GameSection(
            title = "TEAM FORTRESS 2",
            sounds = teamfortressSounds
        )

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
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        // PULSANTE INDIETRO
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
        );

        GameSection(
            title = "MOVIES",
            sounds = movieSounds
        );
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
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        // PULSANTE INDIETRO
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
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                style = TextStyle(
                    drawStyle = Stroke(
                        width = 10f,
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
