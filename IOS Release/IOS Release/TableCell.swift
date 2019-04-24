//
//  TableCell.swift
//  IOS Release
//
//  Created by Tru Nguyen on 4/23/19.
//  Copyright © 2019 Tru Nguyen. All rights reserved.
//

import UIKit

class TableCell: UITableViewCell {

    @IBOutlet weak var img: UIImageView!
    @IBOutlet weak var food_name: UILabel!
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
