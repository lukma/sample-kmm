//
//  ErrorView.swift
//  GPlay
//
//  Created by Lukma Gayo Arizky on 22/08/22.
//

import SwiftUI

struct ErrorView: View {
    var errorMessage: String
    var retryAction: () -> Void
    
    var body: some View {
        VStack {
            LottieView(animation: "CommonErrorAnimation")
                .frame(width: 200, height: 200)
            
            Text(errorMessage)
                .font(.title2)
                .foregroundColor(.primary)
                .multilineTextAlignment(.center)
                .padding(.init(top: 0, leading: 8, bottom: 0, trailing: 8))
            
            Button("Retry", action: retryAction)
                .buttonStyle(.borderedProminent)
        }
        .frame(
            minWidth: 0,
            maxWidth: .infinity,
            minHeight: 0,
            maxHeight: .infinity,
            alignment: .center
        )
    }
}

struct ErrorView_Previews: PreviewProvider {
    static var previews: some View {
        ErrorView(errorMessage: "Oops, something wrong\n please try again!", retryAction: {})
    }
}
