package com.rainads.rainadsapp.ui.scanner

import com.rainads.rainadsapp.ui.scanner.view.ScannerDialog
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ScannerDialogFragmentProvider {
    @ContributesAndroidInjector(modules = [ScannerDialogFragmentModule::class])
    internal abstract fun provideScannerDialogFragment(): ScannerDialog
}