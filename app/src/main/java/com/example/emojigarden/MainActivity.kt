package com.example.emojigarden

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.runtime.*


import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.emojigarden.ui.EmojiGardenTheme
import androidx.compose.runtime.getValue

import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch


@ExperimentalFoundationApi
@ExperimentalMaterialApi
class MainActivity : AppCompatActivity() {

    @ExperimentalMaterialApi
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            setContent {

                // See why login has stopped working
                // Sort out how to wrap up the opening and closing without crashing it lol

                val states = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
                val bottomSheetScope = rememberCoroutineScope()

                val emojiSelectorVm: EmojiSelectorVm = viewModel()

                ModalBottomSheetLayout(
                    sheetContent = {
                        OptionalOptions({ newEmoji ->
                            emojiSelectorVm.onNewEmojiSelected(newEmoji)
                            bottomSheetScope.launch { states.hide() }
                        }, emojiSelectorVm.currentEmoji,
                            {a,b -> emojiSelectorVm.onBioUpdated(a,b)
                                bottomSheetScope.launch { states.hide() } }
                        )
                    },
                    sheetState = states
                ) {

                    val loginVm: LoginVm = viewModel()

                    if (loginVm.showGarden) {
                        Column {
                            val model: EmojiVmRealm = viewModel()
                            MainActivityUi(model.emojiState, model.myToken) { emojiTile ->
                                emojiSelectorVm.onEmojiClicked(emojiTile)
                                bottomSheetScope.launch { states.show() }
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
@Composable
fun EmojiGrid(emojiList: List<EmojiTile>, myToken: String, onEmojiClicked: (EmojiTile) -> Unit) {

    LazyVerticalGrid(cells = GridCells.Adaptive(54.dp)) {
        items(emojiList) { emojiTile ->
            EmojiHolder(emojiTile, myToken, onEmojiClicked)
        }
    }
}

@Composable
fun EmojiHolder(emoji: EmojiTile, myToken: String, onEmojiClicked: (EmojiTile) -> Unit) {
    Log.d("emojiid","Emoji, ${emoji.owner}, Token: $myToken")
    Button(onClick = { onEmojiClicked(emoji) }
    ) {
        Text(emoji.emoji, modifier = Modifier.background(if (emoji.owner == myToken) Color.Red else Color.White , RectangleShape))
    }
}

@Preview
@Composable
fun EmojiPreview() {
    EmojiHolder(EmojiTile().apply { emoji = "😼" }, "") {}
}

@ExperimentalFoundationApi
@Composable
fun MainActivityUi(emojiList: List<EmojiTile>, myToken : String, onEmojiClicked: (EmojiTile) -> Unit) {
    EmojiGardenTheme {
        Box(
            Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            EmojiGrid(emojiList, myToken, onEmojiClicked)
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
    val emojis = listOf("🐤","🐦","🐔","🦤","🕊","️","🦆","🦅","🪶","🦩","🐥","-","🐣","🦉","🦜","🦚","🐧","🐓","🦢","🦃","🦡","🦇","🐻","🦫","🦬","🐈","‍","⬛","🐗","🐪","🐈","🐱","🐿","️","🐄","🐮","🦌","🐕","🐶","🐘","🐑","🦊","🦒","🐐","🦍","🦮","🐹","🦔","🦛","🐎","🐴","🦘","🐨","🐆","🦁","🦙","🦣","🐒","🐵","🐁","🐭","🦧","🦦","🐂","🐼","🐾","🐖","🐷","🐽","🐻","‍","❄","️","🐩","🐇","🐰","🦝","🐏","🐀","🦏","🐕","‍","🦺","🦨","🦥","🐅","🐯","🐫","-","🦄","🐃","🐺","🦓","🐳","🐡","🐬","🐟","🐙","🦭","🦈","🐚","🐳","🐠","🐋","🌱","🌵","🌳","🌲","🍂","🍀","🌿","🍃","🍁","🌴","🪴","🌱","☘","️","🌾","🐊","🐊","🐉","🐲","🦎","🦕","🐍","🦖","-","🐢")
    MainActivityUi(List(102) { i -> EmojiTile().apply { emoji = emojis[i] } },"") {}
}

@ExperimentalFoundationApi
@Composable
fun EmojiSelector(onSelected: (String) -> Unit) {
    val selectableEmojis = listOf("🐤","🐦","🐔","🦤","🕊","️","🦆","🦅","🪶","🦩","🐥","-","🐣","🦉","🦜","🦚","🐧","🐓","🦢","🦃","🦡","🦇","🐻","🦫","🦬","🐈","‍","⬛","🐗","🐪","🐈","🐱","🐿","️","🐄","🐮","🦌","🐕","🐶","🐘","🐑","🦊","🦒","🐐","🦍","🦮","🐹","🦔","🦛","🐎","🐴","🦘","🐨","🐆","🦁","🦙","🦣","🐒","🐵","🐁","🐭","🦧","🦦","🐂","🐼","🐾","🐖","🐷","🐽","🐻","‍","❄","️","🐩","🐇","🐰","🦝","🐏","🐀","🦏","🐕","‍","🦺","🦨","🦥","🐅","🐯","🐫","-","🦄","🐃","🐺","🦓","🐳","🐡","🐬","🐟","🐙","🦭","🦈","🐚","🐳","🐠","🐋","🌱","🌵","🌳","🌲","🍂","🍀","🌿","🍃","🍁","🌴","🪴","🌱","☘","️","🌾","🐊","🐊","🐉","🐲","🦎","🦕","🐍","🦖","-","🐢")
    Box(modifier = Modifier.fillMaxWidth()) {
        LazyVerticalGrid(cells = GridCells.Adaptive(24.dp)) {
            items(selectableEmojis) {
                Text(text = it,
                    Modifier
                        .padding(2.dp)
                        .clickable(onClick = { onSelected(it) }))

            }
        }
    }

}

@Preview
@ExperimentalFoundationApi
@Composable
fun EmojiSelectorPreview() {
    EmojiSelector {}
}

@Composable
fun TextFieldDemo(emojiTile: EmojiTile, onBioUpdated: (String, String) -> Unit) {


        Column(Modifier.padding(16.dp)) {
            var descriptionTextState by remember { mutableStateOf(TextFieldValue(emojiTile.name)) }
            var nameTextState by remember { mutableStateOf(TextFieldValue(emojiTile.bio)) }

            Button(onClick = {onBioUpdated(nameTextState.text,descriptionTextState.text)}){
                Text("Save")
            }

            OutlinedTextField(
                label = { Text("Your Name:") },
                value = nameTextState,
                onValueChange = { nameTextState = it })
            Spacer(Modifier.height(4.dp))
            OutlinedTextField(
                label = { Text("Describe Yourself:") },
                value = descriptionTextState,
                onValueChange = { descriptionTextState = it })
    }
}

@ExperimentalFoundationApi
@Composable
fun OptionalOptions(
    onEmojiSelected: (String) -> Unit,
    currentEmojiTile: EmojiTile?,
    onBioUpdated: (String, String) -> Unit
) {

    DisposableEffect(true){
        onDispose {
            Log.d("dispose effect", "It was disposed")
        }
    }
    Column {
        // TODO
        //  highlight the current emoji when it's being edited.
        //  highlight the current emoji in the normal view when it's your emoji.
        //  fix the problem with the textviews crashing when they're attempted to be loaded.

        EmojiSelector(onEmojiSelected)
        if(currentEmojiTile != null ) {
            TextFieldDemo(currentEmojiTile, onBioUpdated)
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
        Spacer(Modifier.width(4.dp))
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

@Preview(showBackground = true)
@Composable
fun TextFieldPreview() {
    TextFieldDemo(EmojiTile()) { _,_ -> }
}