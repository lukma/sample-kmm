//
//  LottieAnimation.swift
//  GPlay
//
//  Created by Lukma Gayo Arizky on 28/08/22.
//

import SwiftUI
import Lottie

protocol LottieAnimation {
    var animation: String { get }
    var animationView: AnimationView { get }
}

extension LottieAnimation {
    func setupAnimation() {
        animationView.animation = Animation.named(animation)
        animationView.contentMode = .scaleAspectFit
        animationView.loopMode = .loop
        animationView.play()
    }
    
    func setupLayout(heightAnchor: NSLayoutDimension, widthAnchor: NSLayoutDimension) {
        animationView.translatesAutoresizingMaskIntoConstraints = false
        animationView.heightAnchor.constraint(equalTo: heightAnchor).isActive = true
        animationView.widthAnchor.constraint(equalTo: widthAnchor).isActive = true
    }
}
