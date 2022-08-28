//
//  MacosAppDelegate.swift
//  GPlay (macOS)
//
//  Created by Lukma Gayo Arizky on 28/08/22.
//

import SwiftUI

class MacosAppDelegate: NSObject, NSApplicationDelegate, AppDelegateHelper {
    func applicationWillFinishLaunching(_ notification: Notification) {
        initKoin()
    }
}
