package com.example.emojigarden

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.*


import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import com.example.emojigarden.ui.EmojiGardenTheme
import androidx.compose.runtime.getValue

import androidx.compose.runtime.setValue


@ExperimentalFoundationApi
@ExperimentalLayout
class MainActivity : AppCompatActivity() {

    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            // See why login has stopped working
            // Sort out how to wrap up the opening and closing without crashing it lol

            val states = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
            val emojiSelectorVm: EmojiSelectorVm = viewModel()

            ModalBottomSheetLayout(
                sheetContent = {
                    OptionalOptions({ newEmoji ->
                        emojiSelectorVm.onNewEmojiSelected(newEmoji)
                        states.hide()
                    }, emojiSelectorVm.currentEmoji,
                        {a,b -> emojiSelectorVm.onBioUpdated(a,b)
                            states.hide()}
                    )
                },
                sheetState = states
            ) {

                val loginVm: LoginVm = viewModel()

                if (loginVm.showGarden) {
                    Column {
                        val model: EmojiVmRealm = viewModel()
                        MainActivityUi(model.emojiState) { emojiTile ->
                            emojiSelectorVm.onEmojiClicked(emojiTile)
                            states.show()
                        }
                    }
                } else {
                    LoginView(loginVm::login)
                }
            }
        }
    }
}

@ExperimentalFoundationApi
@ExperimentalLayout
@Composable
fun EmojiGrid(emojiList: List<EmojiTile>, onEmojiClicked: (EmojiTile) -> Unit) {

    LazyVerticalGrid(cells = GridCells.Adaptive(54.dp)) {
        items(emojiList) { emojiTile ->
            EmojiHolder(emojiTile, onEmojiClicked)
        }
    }
}

@Composable
fun EmojiHolder(emoji: EmojiTile, onEmojiClicked: (EmojiTile) -> Unit) {
    Button(onClick = { onEmojiClicked(emoji) }
    ) {
        Text(emoji.emoji)
    }
}

@Preview
@Composable
fun EmojiPreview() {
    EmojiHolder(EmojiTile().apply { emoji = "😼" }) {}
}

@ExperimentalFoundationApi
@ExperimentalLayout
@Composable
fun MainActivityUi(emojiList: List<EmojiTile>, onEmojiClicked: (EmojiTile) -> Unit) {
    EmojiGardenTheme {
        Box(
            Modifier.fillMaxSize().padding(16.dp)
        ) {
            EmojiGrid(emojiList, onEmojiClicked)
        }
    }
}

@Composable
fun LoginView(login: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Button(login) {
            Text("Login")
        }
    }
}

@Composable
@Preview(showBackground = true)
fun LoginPreview() {
    LoginView {}
}

@ExperimentalFoundationApi
@ExperimentalLayout
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val emojis = listOf(
        "🐤",
        "🐦",
        "🐔",
        "🦤",
        "🕊",
        "️",
        "🦆",
        "🦅",
        "🪶",
        "🦩",
        "🐥",
        "-",
        "🐣",
        "🦉",
        "🦜",
        "🦚",
        "🐧",
        "🐓",
        "🦢",
        "🦃",
        "🦡",
        "🦇",
        "🐻",
        "🦫",
        "🦬",
        "🐈",
        "‍",
        "⬛",
        "🐗",
        "🐪",
        "🐈",
        "🐱",
        "🐿",
        "️",
        "🐄",
        "🐮",
        "🦌",
        "🐕",
        "🐶",
        "🐘",
        "🐑",
        "🦊",
        "🦒",
        "🐐",
        "🦍",
        "🦮",
        "🐹",
        "🦔",
        "🦛",
        "🐎",
        "🐴",
        "🦘",
        "🐨",
        "🐆",
        "🦁",
        "🦙",
        "🦣",
        "🐒",
        "🐵",
        "🐁",
        "🐭",
        "🦧",
        "🦦",
        "🐂",
        "🐼",
        "🐾",
        "🐖",
        "🐷",
        "🐽",
        "🐻",
        "‍",
        "❄",
        "️",
        "🐩",
        "🐇",
        "🐰",
        "🦝",
        "🐏",
        "🐀",
        "🦏",
        "🐕",
        "‍",
        "🦺",
        "🦨",
        "🦥",
        "🐅",
        "🐯",
        "🐫",
        "-",
        "🦄",
        "🐃",
        "🐺",
        "🦓",
        "🐳",
        "🐡",
        "🐬",
        "🐟",
        "🐙",
        "🦭",
        "🦈",
        "🐚",
        "🐳",
        "🐠",
        "🐋",
        "🌱",
        "🌵",
        "🌳",
        "🌲",
        "🍂",
        "🍀",
        "🌿",
        "🍃",
        "🍁",
        "🌴",
        "🪴",
        "🌱",
        "☘",
        "️",
        "🌾",
        "🐊",
        "🐊",
        "🐉",
        "🐲",
        "🦎",
        "🦕",
        "🐍",
        "🦖",
        "-",
        "🐢"
    )
    MainActivityUi(List(102) { i -> EmojiTile().apply { emoji = emojis[i] } }) {}
}

@ExperimentalLayout
@Composable
fun EmojiSelector(onSelected: (String) -> Unit) {
    // They need to be sorted into Animal, Plant, Person types (all yellow).
    val selectableEmojis = listOf(
        "🐤",
        "🐦",
        "🐔",
        "🦤",
        "🕊",
        "️",
        "🦆",
        "🦅",
        "🪶",
        "🦩",
        "🐥",
        "-",
        "🐣",
        "🦉",
        "🦜",
        "🦚",
        "🐧",
        "🐓",
        "🦢",
        "🦃",
        "🦡",
        "🦇",
        "🐻",
        "🦫",
        "🦬",
        "🐈",
        "‍",
        "⬛",
        "🐗",
        "🐪",
        "🐈",
        "🐱",
        "🐿",
        "️",
        "🐄",
        "🐮",
        "🦌",
        "🐕",
        "🐶",
        "🐘",
        "🐑",
        "🦊",
        "🦒",
        "🐐",
        "🦍",
        "🦮",
        "🐹",
        "🦔",
        "🦛",
        "🐎",
        "🐴",
        "🦘",
        "🐨",
        "🐆",
        "🦁",
        "🦙",
        "🦣",
        "🐒",
        "🐵",
        "🐁",
        "🐭",
        "🦧",
        "🦦",
        "🐂",
        "🐼",
        "🐾",
        "🐖",
        "🐷",
        "🐽",
        "🐻",
        "‍",
        "❄",
        "️",
        "🐩",
        "🐇",
        "🐰",
        "🦝",
        "🐏",
        "🐀",
        "🦏",
        "🐕",
        "‍",
        "🦺",
        "🦨",
        "🦥",
        "🐅",
        "🐯",
        "🐫",
        "-",
        "🦄",
        "🐃",
        "🐺",
        "🦓",
        "🐳",
        "🐡",
        "🐬",
        "🐟",
        "🐙",
        "🦭",
        "🦈",
        "🐚",
        "🐳",
        "🐠",
        "🐋",
        "🌱",
        "🌵",
        "🌳",
        "🌲",
        "🍂",
        "🍀",
        "🌿",
        "🍃",
        "🍁",
        "🌴",
        "🪴",
        "🌱",
        "☘",
        "️",
        "🌾",
        "🐊",
        "🐊",
        "🐉",
        "🐲",
        "🦎",
        "🦕",
        "🐍",
        "🦖",
        "-",
        "🐢"
    )
    // [animal], [plant], [person]
    // Four items only at first. Same general structure.
    Box(modifier = Modifier.fillMaxWidth()) {
        FlowRow() {
            selectableEmojis.forEach {
                Text(text = it, Modifier.padding(2.dp).clickable(onClick = { onSelected(it) }))

            }
        }
    }

}

@ExperimentalLayout
@Preview
@Composable
fun EmojiSelectorPreview() {
    EmojiSelector {}
}

@Composable
fun TextFieldDemo(emojiTile: EmojiTile, onBioUpdated: (String, String) -> Unit) {

        Column(Modifier.padding(16.dp)) {
            var descriptionTextState by remember { mutableStateOf(TextFieldValue(emojiTile.name)) }
            var nameTextState by remember { mutableStateOf(TextFieldValue(emojiTile.bio)) }

            onDispose(callback = {
                onBioUpdated(nameTextState.text,descriptionTextState.text)
            })

            OutlinedTextField(
                label = { Text("Your Name:") },
                value = nameTextState,
                onValueChange = { nameTextState = it })
            Spacer(Modifier.preferredHeight(4.dp))
            OutlinedTextField(
                label = { Text("Describe Yourself:") },
                value = descriptionTextState,
                onValueChange = { descriptionTextState = it })
        }


}

@ExperimentalLayout
@Composable
fun OptionalOptions(
    onEmojiSelected: (String) -> Unit,
    currentEmojiTile: EmojiTile?,
    onBioUpdated: (String, String) -> Unit
) {
    var tabSelected : EmojiSelectOptions by remember { mutableStateOf(EmojiSelectOptions.EDIT_EMOJI) }
    Column {
        // TODO
        //  highlight the current emoji when it's being edited.
        //  highlight the current emoji in the normal view when it's your emoji.
        //  fix the problem with the textviews crashing when they're attempted to be loaded.


        // Only the tab headings
        TabRow(selectedTabIndex = tabSelected.ordinal ) {
            EmojiSelectOptions.values().forEach { selectOption ->
                Tab(
                    selected = selectOption == tabSelected,
                    onClick = { tabSelected = selectOption },
                    text = { Text(selectOption.tabName) })
            }
        }

        // Actual tab content
        when (tabSelected) {
            EmojiSelectOptions.EDIT_EMOJI -> EmojiSelector(onEmojiSelected)
            EmojiSelectOptions.EDIT_DESCRIPTION -> TextFieldDemo(currentEmojiTile!!, onBioUpdated)
        }


    }
    // if it's yours then you see all this.
    //      otherwise you see an entirely different view.
            // [] Name
            //    Description
    // All this is what you see if it's either yours or something that can be yours.

}

@Composable
fun OtherPersonsTileDetail(emojiTile: EmojiTile) {
    Row {
        Text(emojiTile.emoji)
        Spacer(Modifier.preferredWidth(4.dp))
        Column {
           Text("Name")
           Text("Description")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SeeOtherPersonsComposable() {
    OtherPersonsTileDetail(emojiTile = EmojiTile().apply { emoji = "🐴"  })
}

enum class EmojiSelectOptions(val tabName : String) {
    EDIT_EMOJI("Select Emoji"),
    EDIT_DESCRIPTION("Edit Description"),
    VIEW_OTHER_PERSONS_DESCRIPTION("Buddy")
}

sealed class OptionTabs {
    object Emoji : OptionTabs()
    object Description : OptionTabs()
}


@Preview(showBackground = true)
@Composable
fun TextFieldPreview() {
    TextFieldDemo(EmojiTile()) { _,_ -> }
}