//
//  MacosLottieView.swift
//  GPlay (macOS)
//
//  Created by Lukma Gayo Arizky on 28/08/22.
//

import SwiftUI
import Lottie

struct MacosLottieView: NSViewRepresentable, LottieAnimation {
    var animation: String
    var animationView: AnimationView = AnimationView()

    func makeNSView(context: NSViewRepresentableContext<MacosLottieView>) -> NSView {
        let view = NSView(frame: .zero)
        
        setupAnimation()
        view.addSubview(animationView)
        setupLayout(heightAnchor: view.heightAnchor, widthAnchor: view.widthAnchor)

        return view
    }

    func updateNSView(_ uiView: NSView, context: NSViewRepresentableContext<MacosLottieView>) {
        // noop
    }
}
