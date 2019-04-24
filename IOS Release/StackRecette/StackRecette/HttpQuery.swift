//
//  HttpQuery.swift
//  StackRecette
//
//  Created by Rudy on 4/24/19.
//  Copyright © 2019 Rudy. All rights reserved.
//
import UIKit

class HttpQuery
{
    let ingre: String
    let page: Int
    
    
    init(ingre: String, page: Int)
    {
        self.ingre = ingre
        self.page = page
    }
    
    func getFood(callback: @escaping ([dish]?) -> ())
    {
        
        var dishArr = [dish]()
        
        guard let url = URL(string: "http://www.recipepuppy.com/api/?i=onions,garlic&q=omelet&p=3") else {
            return
        }
        
        let session = URLSession.shared
        session.dataTask(with: url) { (data,res,err) in
            
            if let data = data {
                do {
                    let json = try JSONSerialization.jsonObject(with: data, options: [])
                    
                    guard let dictionary = json as? [String:Any]  else {
                        return
                    }
                    
                    guard let meal = dictionary["results"] as? [[String:Any]] else {
                        return
                    }
                    
                    for p in meal {
                        guard let titles = p["title"] as? String else { return }
                        guard let ings = p["ingredients"] as? String else { return }
                        guard let thumbnails = p["thumbnail"] as? String else { return }
                        
                        dishArr.append(dish(title: titles, ing: ings, thumbnail: thumbnails))
                    }
                    
                    callback(dishArr)
                    
                } catch {
                    print(error)
                }
            }
            }.resume()
    }
}

class dish
{
    let title: String
    let ing: String
    let thumbnail: String
    
    init(title:String, ing: String, thumbnail: String)
    {
        self.title = title.trimmingCharacters(in: .whitespacesAndNewlines)
        self.ing = ing.trimmingCharacters(in: .whitespacesAndNewlines)
        self.thumbnail = thumbnail.trimmingCharacters(in: .whitespacesAndNewlines)
    }
    
    func print_dish()
    {
        print(self.title + " " + self.ing + " ")
    }
}
