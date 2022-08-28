//
//  LoadMoreView.swift
//  GPlay
//
//  Created by Lukma Gayo Arizky on 22/08/22.
//

import SwiftUI

struct LoadMoreView: View {
    var body: some View {
        ZStack {
            ProgressView("Loading…")
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

struct LoadMoreView_Previews: PreviewProvider {
    static var previews: some View {
        LoadMoreView()
    }
}
