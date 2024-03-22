const Clothes = require('../Model/clothes');
const clothesController = {
    getClothes: async(req, res) =>{
        try {
            const newFruit = new Clothes(req.body);
            const saveFruit = await newFruit.save();
            // res.status(200).json(saveTruit);
            if (saveFruit) {
                res.json({
                    "status": 200,
                    "messenger": "Them thanh cong",
                    "data": saveFruit
                })
            } else {
                res.json({
                    "status": 400,
                    "messenger": "Loi, them khong thanh cong",
                    "data": []
                })
            }
        } catch (error) {
            res.status(500).json(error);
        }
    },
    addFruitWithFileImage: async (req, res) =>{
      try {
        const data = req.body;
        const files = req.files;
        const urlsImage = files.map((file) => `${req.protocol}://${req.get("host")}/upload/${file.filename}`);
        const newClothes = new Clothes({
            type: data.type,
            name: data.name,
            price: data.price,
            image: urlsImage
        });
        const result = await newClothes.save();
        if(result){
            return res.json({
                status: 200,
                messenger: "them thanh cong",
                data: result
            });
        }else{
            return res.json({
                status: 400,
                messenger: "Loi, the, khong thanh cong",
                data: []
            })
        }
      } catch (error) {
        return res.status(500).json({error: error.messenger});
      }         
    },
}
module.exports = clothesController