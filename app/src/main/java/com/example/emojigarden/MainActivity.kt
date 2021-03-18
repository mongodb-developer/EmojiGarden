package com.example.emojigarden


import android.os.Bundle
import android.util.Log
import android.widget.Space
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.emojigarden.ui.EmojiGardenTheme
import kotlinx.coroutines.launch


@ExperimentalFoundationApi
@ExperimentalMaterialApi
class MainActivity : AppCompatActivity() {

    @ExperimentalMaterialApi
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val snackHostState = remember { SnackbarHostState() }

            val modalBottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

            val bottomSheetScope = rememberCoroutineScope()

            val snackBarScope = rememberCoroutineScope()

            val emojiSelectorVm: EmojiSelectorVm = viewModel()

            ModalBottomSheetLayout(
                sheetState = modalBottomSheetState,
                sheetContent = {
                    OptionalOptions(
                        emojiSelectorVm.currentEmoji,
                        emojiSelectorVm.currentTileType
                    ) {
                        emojiSelectorVm.save()
                        Log.d("sheetopen", "Hide")
                        bottomSheetScope.launch { modalBottomSheetState.hide()}
                            .invokeOnCompletion { Log.d("scope", "Closing. Value now ${modalBottomSheetState.currentValue}, target value ${modalBottomSheetState.targetValue}") }
                    }
                }
            ) {

                val loginVm: LoginVm = viewModel()

                if (loginVm.showGarden) {
                    Column {
                        val model: EmojiVmRealm = viewModel()
                        MainActivityUi(model.emojiState, model::isOwnTile) { emojiTile ->
                            emojiSelectorVm.onEmojiClicked(emojiTile)
                        }
                    }
                } else {
                    LoginView(loginVm::login)
                }

                SnackbarHost(hostState = snackHostState)

                if (emojiSelectorVm.currentEmoji.value != null) {
                    when (emojiSelectorVm.currentTileType) {
                        EmojiSelectorVm.TileType.FREE_TILE_LIMIT_REACHED -> {
                            snackBarScope.launch {
                                snackHostState.showSnackbar(
                                    "You have a tile"
                                )
                            }
                        }

                        EmojiSelectorVm.TileType.EDITABLE, EmojiSelectorVm.TileType.OTHER_PERSONS_TILE -> {
                            Log.d("sheetopen", "Launching sheet")
                            bottomSheetScope.launch {
                                modalBottomSheetState.show()
                                Log.d(
                                    "scope",
                                    "Current value ${modalBottomSheetState.currentValue}, final value ${modalBottomSheetState.targetValue}, is animating ${modalBottomSheetState.isAnimationRunning}"
                                )
                            }.invokeOnCompletion { Log.d("scope", "Now completed") }
                        }
                    }
                }

            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun EmojiGrid(
    emojiList: List<EmojiTile>,
    isOwnEmoji: (EmojiTile) -> Boolean,
    onEmojiClicked: (EmojiTile) -> Unit
) {

    LazyVerticalGrid(cells = GridCells.Adaptive(54.dp)) {
        items(emojiList) { emojiTile ->
            EmojiHolder(emojiTile, isOwnEmoji, onEmojiClicked)
        }
    }
}

@Composable
fun EmojiHolder(
    emoji: EmojiTile,
    isOwnEmoji: (EmojiTile) -> Boolean,
    onEmojiClicked: (EmojiTile) -> Unit
) {
    Button(onClick = { onEmojiClicked(emoji) }
    ) {
        Text(
            emoji.emoji,
            modifier = Modifier.background(
                if (isOwnEmoji(emoji)) Color.Red else Color.White,
                RectangleShape
            )
        )
    }
}

@Preview
@Composable
fun EmojiPreview() {
    EmojiHolder(EmojiTile().apply { emoji = "😼" }, { true }) {}
}

@ExperimentalFoundationApi
@Composable
fun MainActivityUi(
    emojiList: List<EmojiTile>,
    isOwnEmoji: (EmojiTile) -> Boolean,
    onEmojiClicked: (EmojiTile) -> Unit
) {
    EmojiGardenTheme {
        Box(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            EmojiGrid(emojiList, isOwnEmoji, onEmojiClicked)
        }
    }
}

@Composable
fun LoginView(login: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
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
    MainActivityUi(List(102) { i -> EmojiTile().apply { emoji = emojis[i] } }, { true }) {}
}

@ExperimentalFoundationApi
@Composable
fun EmojiSelector(emojiTile: MutableState<EmojiTile?>) {
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
    Box(modifier = Modifier.fillMaxWidth()) {
        LazyVerticalGrid(cells = GridCells.Adaptive(24.dp)) {
            items(selectableEmojis) {
                Text(
                    text = it,
                    Modifier
                        .padding(2.dp)
                        .clickable(onClick = { emojiTile.value?.emoji = it })
                )

            }
        }
    }

}

@Preview
@ExperimentalFoundationApi
@Composable
fun EmojiSelectorPreview() {
    EmojiSelector(mutableStateOf(EmojiTile()))
}

@Composable
fun TextFieldDemo(emojiTile: MutableState<EmojiTile?>) {

    Column(Modifier.padding(16.dp)) {

        OutlinedTextField(
            label = { Text("Your Name:") },
            value = emojiTile.value!!.name,
            onValueChange = { change ->
                emojiTile.value = emojiTile.value?.apply { name = change }
            }
        )
        Spacer(Modifier.height(4.dp))
        OutlinedTextField(
            label = { Text("Describe Yourself:") },
            value = emojiTile.value!!.bio,
            onValueChange = { change ->
                emojiTile.value = emojiTile.value?.apply { bio = change }
            }
        )
    }
}

@ExperimentalFoundationApi
@Composable
fun OptionalOptions(
    currentEmojiTile: MutableState<EmojiTile?>,
    tileType: EmojiSelectorVm.TileType?,
    save: () -> Unit
) {
    Log.d("sheetopen", "Started, let's see $tileType")

    if(currentEmojiTile.value != null ) {
        when (tileType) {
            EmojiSelectorVm.TileType.EDITABLE -> {

                Column {

                    Button(save) {
                        Text("Save")
                    }
                    Log.d("sheetopen", "Current tile in editable: ${currentEmojiTile.value?.emoji}")
                    EmojiSelector(currentEmojiTile)
                    TextFieldDemo(currentEmojiTile)
                }
            }
            EmojiSelectorVm.TileType.OTHER_PERSONS_TILE -> OtherPersonsTileDetail(
                currentEmojiTile
            )
            else -> Text("open $tileType, ${currentEmojiTile.value?.emoji}")/*Spacer(Modifier.height(4.dp).background(Color.Red))*/ // If nothing is rendered in the sheet, it will crash the app.
        }
    } else {
        Spacer(Modifier.height(4.dp))
    }

}

@Composable
fun OtherPersonsTileDetail(emojiTile: MutableState<EmojiTile?>) {

    Row {
        Text(emojiTile.value!!.emoji)
        Spacer(Modifier.width(4.dp))
        Column {
            Text("Name: ${emojiTile.value!!.name}")
            Text("Bio: ${emojiTile.value!!.bio}")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SeeOtherPersonsComposable() {
    OtherPersonsTileDetail(emojiTile = mutableStateOf(EmojiTile().apply { emoji = "🐴" }))
}

@Preview(showBackground = true)
@Composable
fun TextFieldPreview() {
    TextFieldDemo(mutableStateOf(null))
}