![alt text](/images/active_light.png "Active State - Light")
![alt text](/images/warning_light.png "Warning State - Light")
![alt text](/images/warning_dark.png "Warning State - Dark")
![alt text](/images/locked_light.png "Locked State - Light")
![alt text](/images/locked_dark.png "Locked State - Dark")

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