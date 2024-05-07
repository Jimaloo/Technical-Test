<div style="display: flex; justify-content: space-between;">
  <img src="/images/active_light.png" alt="Active State - Light" width="200" height="auto">
  <img src="/images/warning_light.png" alt="Warning State - Light" width="200" height="auto">
  
  <img src="/images/locked_light.png" alt="Locked State - Light" width="200" height="auto">
</div>
<div style="display: flex; justify-content: space-between;">
  
  <img src="/images/warning_dark.png" alt="Warning State - Dark" width="200" height="auto">
  <img src="/images/locked_dark.png" alt="Locked State - Dark" width="200" height="auto">
</div>

# M-kopa
To include the devicecountdowntimer module to your module. Add the following line to your app's
build.gradle.kts

` implementation(project(":devicecountdowntimer")) `

In your screen, instanciate the viewModel.
` val viewModel: TimerScreenViewModel = viewModel<TimerScreenViewModel>() `

Trigger the start timer using a Launched effect.
` LaunchedEffect(key1 = true, block = { `
`    viewModel.onEventChange(TimerScreenUiEvent.StartTimer) `
`}) `

Pass the state to the TimerScreen composable
` TimerScreen(state = viewModel.state.value) `

# Notes

- Should the UI be decentralized or centralized in the devicecountdowntimer module?
-

# Things to include in the future

1. Test the color changes in the UI tests.
