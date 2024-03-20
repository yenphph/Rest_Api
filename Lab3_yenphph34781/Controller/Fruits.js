const Fruits = require('../Model/Fruits');
const FruitsController = {
    //add
    addFruits: async (req, res) => {
        try {
            const newFruit = new Fruits(req.body);
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
    getFruits: async (req, res) => {
        try {
            const data = await Fruits.find();
            res.json({
                "status": 200,
                "messenger": "Danh sach fruit",
                "data": data
            })
        } catch (error) {
            console.log(error);
        }
    },
    //get an fruit
    getFruit: async (req, res) => {
        try {
            const fruit = await Fruits.findById(req.params.id).populate('id_distributor');

            res.json({
                "status": 200,
                "messenger": "Danh sach fruit",
                "data": fruit
            })
        } catch (error) {
            console.log(error);
        }
    },

    getGiaSX: async (req, res) => {
        try {
            const { price_start, price_end } = req.query;
            const query = {
                price: { $gte: parseFloat(price_start), $lte: parseFloat(price_end) }
            };

            const data = await Fruits.find(query, "name quantity price id_distributor")
                .populate('id_distributor')
                .sort({ quantity: -1 })
                .skip(0)
                .limit(2);

            res.json({
                status: 200,
                message: "Dữ liệu theo giá sản xuất",
                data: data
            });
        } catch (error) {
            console.log(error);
            res.status(500).json(error);
        }
    },

    getQueryhai: async (req, res) => {
        try {
            const query2 = {
                name: { $regex: '^P', $options: 'i' } // Tìm kiếm tất cả các mục có chữ cái bắt đầu là 'P' (không phân biệt chữ hoa, chữ thường)
            };
    
            const dataS = await Fruits.find(query2, 'name quantity price id_distributor')
                .populate('id_distributor');
    
            res.json({
                "status": 200,
                "message": "Danh sách fruit",
                "data": dataS
            });
        } catch (error) {
            console.log(error);
            res.status(500).json(error);
        }
    },
    
    update: async (req, res) => {
        // try {
        //     const data = await Fruits.findById(req.params.id);
        //     await data.updateOne({ $set: req.body });
        //     res.status(200).json("Updated successfully!");
        // } catch (err) {
        //     res.status(500).json(err);
        // }
        try {
            const { id } = req.params;
            const data = req.body;

            // Tìm đối tượng Fruits cần cập nhật
            const updateFruit = await Fruits.findById(id);

            if (!updateFruit) {
                return res.status(404).json({
                    "status": 404,
                    "message": "Không tìm thấy sản phẩm để cập nhật",
                    "data": []
                });
            }

            // Cập nhật thông tin
            updateFruit.name = data.name ?? updateFruit.name;
            updateFruit.quantity = data.quantity ?? updateFruit.quantity;
            updateFruit.price = data.price ?? updateFruit.price;
            updateFruit.status = data.status ?? updateFruit.status;
            updateFruit.image = data.image ?? updateFruit.image;
            updateFruit.description = data.description ?? updateFruit.description;
            updateFruit.id_distributor = data.id_distributor ?? updateFruit.id_distributor;

            // Lưu lại vào cơ sở dữ liệu
            const updatedFruit = await updateFruit.save();

            // Trả về thông tin về đối tượng đã được cập nhật
            res.json({
                "status": 200,
                "message": "Cập nhật thành công",
                "data": updatedFruit
            });
        } catch (error) {
            console.log(error);
            res.status(500).json({
                "status": 500,
                "message": "Lỗi, Có lỗi xảy ra khi cập nhật",
                "data": []
            });
        }
    },
     addFruitWithFileImage : async (req, res) => {
        try {
            const data = req.body;
            const files = req.files; // Đổi req thành req.files để lấy danh sách các files đã upload
    
            const urlsImage = files.map((file) => `${req.protocol}://${req.get("host")}/uploads/${file.filename}`);
    
            const newFruit = new Fruits({
                name: data.name,
                quantity: data.quantity,
                price: data.price,
                status: data.status,
                image: urlsImage,
                description: data.description,
                id_distributor: data.id_distributor
            });
    
            const result = await newFruit.save();
    
            if (result) {
                return res.json({
                    status: 200,
                    messenger: "Thêm thành công",
                    data: result
                });
            } else {
                return res.json({
                    status: 400,
                    messenger: "Lỗi, thêm không thành công",
                    data: []
                });
            }
        } catch (error) {
            return res.status(500).json({ error: error.message });
        }
     }
    
}
module.exports = FruitsController