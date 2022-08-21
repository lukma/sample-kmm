//
//  ArticleItem.swift
//  GPlay
//
//  Created by Lukma Gayo Arizky on 22/08/22.
//

import SwiftUI
import common

struct ArticleItem: View {
    var article: Article
    
    var body: some View {
        VStack(alignment: .leading) {
            AsyncImage(url: URL(string: article.thumbnail))
                .aspectRatio(contentMode: .fit)
            
            Text(article.title)
                .font(.title)
                .fontWeight(.semibold)
                .foregroundColor(.primary)
                .padding(.init(top: 0, leading: 8, bottom: 0, trailing: 8))
            
            Text(article.content.stripOutHtml() ?? "")
                .font(.body)
                .foregroundColor(.secondary)
                .lineLimit(3)
                .padding(.init(top: 0, leading: 8, bottom: 8, trailing: 8))
        }
        .frame(
            minWidth: 0,
            maxWidth: .infinity,
            minHeight: 0,
            maxHeight: .infinity,
            alignment: .topLeading
        )
        .background(.white)
        .cornerRadius(16)
    }
}

struct ArticleItem_Previews: PreviewProvider {
    static var previews: some View {
        let article = Article(
            id: "1",
            title: "Lorem ipsum",
            content: "<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris sagittis molestie orci at scelerisque. Vestibulum finibus iaculis pellentesque. Phasellus ornare commodo purus in sagittis. In sodales pretium orci non facilisis. Ut molestie egestas neque, in viverra arcu pellentesque sit amet. Aliquam auctor turpis ac sagittis tincidunt. Vivamus sed elit vitae risus ultricies dapibus sodales ut libero. Fusce ultricies mauris mauris, et congue ipsum posuere ut. Vestibulum facilisis felis felis, in faucibus nibh tincidunt vel. Duis posuere consectetur bibendum. Suspendisse nunc lacus, elementum id congue vitae, ultricies aliquet massa. Suspendisse suscipit non mauris eget tincidunt. Suspendisse eu erat at metus rutrum scelerisque. Aenean ligula nisl, mattis a erat sit amet, ullamcorper auctor tellus.</p>",
            thumbnail: "https://picsum.photos/id/11/700",
            createdAt: "2022-12-31T12:00:00.000124Z"
        )
        ArticleItem(article: article)
    }
}
