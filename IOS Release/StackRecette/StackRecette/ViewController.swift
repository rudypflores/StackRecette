//
//  ViewController.swift
//  StackRecette
//
//  Created by Rudy on 4/24/19.
//  Copyright © 2019 Rudy. All rights reserved.
//

import UIKit
import GoogleSignIn

class ViewController: UIViewController, GIDSignInUIDelegate, UITableViewDelegate, UITableViewDataSource, UITextFieldDelegate {
    
    @IBOutlet weak var signOutButton: UIButton!
    @IBOutlet var table: UITableView!
    @IBOutlet weak var sb: UITextField!
    
    
    let delegate = UIApplication.shared.delegate as! AppDelegate
    
    var foodArr = [food]() {
        didSet {
            DispatchQueue.main.async {
                self.table.reloadData()
            }
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        sb.delegate = self
        
        
        if delegate.signedIn == false {
            signOutButton.isHidden = true
        } else {
            signOutButton.isHidden = false
        }
        

        // Do any additional setup after loading the view, typically from a nib.
    }
    
    //Handle signout
    @IBAction func didTapSignOut(_ sender: AnyObject) {
        GIDSignIn.sharedInstance()?.signOut()
        delegate.signedIn = false
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return foodArr.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        guard let cell = tableView.dequeueReusableCell(withIdentifier: "Cell") as? TableCell else {
            return UITableViewCell()
        }
        
        if let data: Data = getData(img: foodArr[indexPath.row].img) {
            cell.food_name.text = foodArr[indexPath.row].food_name
            cell.img.image = UIImage(data: data)
        } else {
            var img = foodArr[indexPath.row].img
            if(img.isEmpty) {
                img = "2"
            } else {
                img = foodArr[indexPath.row].img
            }
            
            cell.food_name.text = foodArr[indexPath.row].food_name
            cell.img.image = UIImage(named: img)
        }
        
        
        return cell
    }

    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        if sb.text?.count != 0 {
            print(sb.text!)
            let a = HttpQuery(ingre: sb.text!, page: 1)
            a.getFood() { data in
                if let data = data {
                    self.foodArr.removeAll()
                    for i in data {
                        self.foodArr.append(food(food_name: i.title, img:i.thumbnail))
                    }
                }
            }
        }
        
        return true
    }
    
    
    
    func getData (img: String) -> Data? {
        if let url = URL(string: img) {
            do {
                let data = try Data(contentsOf: url)
                return data
            } catch let err {
                print("error : \(err.localizedDescription)")
            }
        }
        return nil
    }
    
    @IBAction func unwindToMain(_ sender: UIStoryboardSegue) {}
    
}
