//
//  LottieView.swift
//  GPlay
//
//  Created by Lukma Gayo Arizky on 22/08/22.
//

import SwiftUI

struct LottieView: View {
    let animation: String
    
    var body: some View {
        #if os(iOS)
        IosLottieView(animation: animation)
        #elseif os(OSX)
        MacosLottieView(animation: animation)
        #endif
    }
}
