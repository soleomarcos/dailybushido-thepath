
package com.soleostudio.dailybushido_thepath

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      MaterialTheme {
        Surface(Modifier.fillMaxSize()) {
          PreviewScreen()
        }
      }
    }
  }
}

@Composable
private fun PreviewScreen() {
  var med by rememberSaveable { mutableStateOf(false) }
  var posture by rememberSaveable { mutableStateOf(false) }
  var reflection by rememberSaveable { mutableStateOf(false) }

  val options = listOf(
    "Padronizar punições",
    "Reduzir rituais",
    "Alinhar fala e ação", // correta (índice 2)
    "Promover fama"
  )
  var selected by rememberSaveable { mutableStateOf<Int?>(null) }

  var streak by rememberSaveable { mutableStateOf(0) }
  var best by rememberSaveable { mutableStateOf(0) }
  var result by rememberSaveable { mutableStateOf<String?>(null) }

  Column(
    modifier = Modifier.fillMaxSize().padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    Text(
      "Daily Bushido — Prévia",
      style = MaterialTheme.typography.titleLarge,
      fontWeight = FontWeight.SemiBold
    )

    Card {
      Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("正名", style = MaterialTheme.typography.displaySmall)
        Text(
          "zhèngmíng",
          style = MaterialTheme.typography.bodyMedium,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
          "Alinhar nomes, papéis e prática. Sem nomes corretos, palavras não viram ação.",
          style = MaterialTheme.typography.bodyMedium
        )
      }
    }

    Card {
      Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Checklist real (mínimo 60%)", style = MaterialTheme.typography.titleMedium)
        Row(verticalAlignment = Alignment.CenterVertically) {
          Checkbox(checked = med, onCheckedChange = { med = it })
          Spacer(Modifier.width(8.dp)); Text("Meditação")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
          Checkbox(checked = posture, onCheckedChange = { posture = it })
          Spacer(Modifier.width(8.dp)); Text("Postura/Exercício")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
          Checkbox(checked = reflection, onCheckedChange = { reflection = it })
          Spacer(Modifier.width(8.dp)); Text("Reflexão")
        }
      }
    }

    Card {
      Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text("Quiz (mínimo 70%)", style = MaterialTheme.typography.titleMedium)
        Text("Por que 'retificar os nomes' precede governar?", style = MaterialTheme.typography.bodyMedium)
        options.forEachIndexed { idx, label ->
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .selectable(
                selected = selected == idx,
                onClick = { selected = idx },
                role = Role.RadioButton
              )
              .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            RadioButton(selected = selected == idx, onClick = { selected = idx })
            Spacer(Modifier.width(8.dp))
            Text(label)
          }
        }
      }
    }

    Row(horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
      Button(
        onClick = {
          val realScore =
            0.4f * (if (med) 1 else 0) +
            0.4f * (if (posture) 1 else 0) +
            0.2f * (if (reflection) 1 else 0)
          val quizScore = if (selected == 2) 1.0f else 0.0f
          val pass = (realScore >= 0.60f) && (quizScore >= 0.70f)

          if (pass) {
            streak += 1
            if (streak > best) best = streak
            result = "Dia válido. Real: ${(realScore * 100).toInt()}% • Quiz: ${(quizScore * 100).toInt()}% • Streak: $streak (melhor: $best)"
          } else {
            streak = 0
            result = "Dia inválido. Real: ${(realScore * 100).toInt()}% • Quiz: ${(quizScore * 100).toInt()}% • Streak reiniciado"
          }
        }
      ) { Text("Concluir dia") }

      OutlinedButton(onClick = {
        med = false; posture = false; reflection = false; selected = null
        streak = 0; best = 0; result = null
      }) { Text("Zerar") }
    }

    Text("Streak atual: $streak (melhor: $best)", style = MaterialTheme.typography.bodyMedium)
    result?.let {
      Text(it, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
  }
}
