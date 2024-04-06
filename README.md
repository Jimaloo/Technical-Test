![Active State - Light](/images/active_light.png =100x) ![Warning State - Light](/images/warning_light.png =100x) ![Warning State - Dark](/images/warning_dark.png =100x) ![Locked State - Light](/images/locked_light.png =100x) ![Locked State - Dark](/images/locked_dark.png =100x)


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