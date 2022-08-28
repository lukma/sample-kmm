//
//  IosLottieView.swift
//  GPlay (iOS)
//
//  Created by Lukma Gayo Arizky on 28/08/22.
//

import SwiftUI
import Lottie

struct IosLottieView: UIViewRepresentable, LottieAnimation {
    var animation: String
    var animationView: AnimationView = AnimationView()
    
    func makeUIView(context: Context) -> some UIView {
        let view = UIView()
        
        setupAnimation()
        view.addSubview(animationView)
        setupLayout(heightAnchor: view.heightAnchor, widthAnchor: view.widthAnchor)
        
        return view
    }
    
    func updateUIView(_ uiView: UIViewType, context: Context) {
        // noop
    }
}
