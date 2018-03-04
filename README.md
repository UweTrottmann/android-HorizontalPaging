## Support Library 27.1.0 Loader bug

https://issuetracker.google.com/issues/74136006

### Behavior in 27.1.0
```
// tabs 1,2,3
// startup
D/MainActivity/DummySectionFragment1: initLoader
D/MainActivity/DummySectionFragment1: onCreateLoader
D/MainActivity/DummySectionFragment1: onStart
D/MainActivity/DummySectionFragment2: initLoader
D/MainActivity/DummySectionFragment2: onCreateLoader
D/MainActivity/DummySectionFragment1: onLoadFinished
//
// swipe to 2nd tab
D/MainActivity/DummySectionFragment2: onStart
D/MainActivity/DummySectionFragment3: initLoader
D/MainActivity/DummySectionFragment3: onCreateLoader
D/MainActivity/DummySectionFragment2: onLoadFinished
//
// to 3rd tab
D/MainActivity/DummySectionFragment3: onStart
D/MainActivity/DummySectionFragment3: onLoadFinished
//
// to 2nd tab
D/MainActivity/DummySectionFragment1: initLoader
D/MainActivity/DummySectionFragment1: onStart
D/MainActivity/DummySectionFragment1: onLoadFinished
D/MainActivity/DummySectionFragment1: onLoadFinished <-- called twice?
//
// when break point in onLoadFinished, is even called more often
// +1 for each time swiped to 3rd and back (1st fragment is destroyed + rebuilt)
// LoaderInfo.mObservers size continues to grow instead of staying at size 1
// --> onLoadFinished called multiple times
//
// to 1st tab, then 2nd tab
D/MainActivity/DummySectionFragment3: initLoader
D/MainActivity/DummySectionFragment3: onStart
D/MainActivity/DummySectionFragment3: onLoadFinished
...
```

### Behavior in 27.0.2

```
// tabs 1,2,3
// startup
D/MainActivity/DummySectionFragment1: initLoader
D/MainActivity/DummySectionFragment1: onCreateLoader
D/MainActivity/DummySectionFragment1: onStart
D/MainActivity/DummySectionFragment2: initLoader
D/MainActivity/DummySectionFragment2: onCreateLoader
D/MainActivity/DummySectionFragment1: onLoadFinished
D/MainActivity/DummySectionFragment2: onStart
D/MainActivity/DummySectionFragment2: onLoadFinished
//
// swipe to 2nd tab
D/MainActivity/DummySectionFragment3: initLoader
D/MainActivity/DummySectionFragment3: onCreateLoader
D/MainActivity/DummySectionFragment3: onStart
D/MainActivity/DummySectionFragment3: onLoadFinished
//
// to 3rd tab, then to 2nd tab
D/MainActivity/DummySectionFragment1: initLoader
D/MainActivity/DummySectionFragment1: onStart
D/MainActivity/DummySectionFragment1: onLoadFinished
//
// to 1st tab, then to 2nd tab
D/MainActivity/DummySectionFragment3: initLoader
D/MainActivity/DummySectionFragment3: onStart
D/MainActivity/DummySectionFragment3: onLoadFinished
...
```
