//
//  ViewController.swift
//  IOS Release
//
//  Created by Tru Nguyen on 4/23/19.
//  Copyright © 2019 Tru Nguyen. All rights reserved.
//

import UIKit
import GoogleSignIn

<<<<<<< HEAD
class ViewController: UIViewController, GIDSignInUIDelegate {
    
    @IBOutlet weak var signOutButton: UIButton!
    
    let delegate = UIApplication.shared.delegate as! AppDelegate
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
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
=======
class ViewController: UIViewController, UITableViewDelegate, UITableViewDataSource {
    
    @IBOutlet var table: UITableView!
    @IBOutlet weak var searchBar: UISearchBar!

    var foodArr = [food]()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        setUpFood()
        // Do any additional setup after loading the view, typically from a nib.
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
            cell.food_name.text = foodArr[indexPath.row].food_name
            cell.img.image = UIImage(named: foodArr[indexPath.row].img)
        }
        
        
        return cell
    }
    
    func setUpFood()
    {
        foodArr.append(food(food_name: "cc", img:"http://img.recipepuppy.com/514820.jpg"))
        foodArr.append(food(food_name: "bs", img:"2"))
        foodArr.append(food(food_name: "ccdd", img:"http://img.recipepuppy.com/514820.jpg"))
        foodArr.append(food(food_name: "ccw", img:"http://img.recipepuppy.com/514820.jpg"))
    }
    
    func getData (img: String) -> Data?
    {
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
    
>>>>>>> 14ca6cb2fc125c445aafe667b7d5237a46031e02
    
    @IBAction func unwindToMain(_ sender: UIStoryboardSegue) {
    }
}
