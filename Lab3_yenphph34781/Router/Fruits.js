const truitsController = require('../Controller/Fruits');
const upload = require('../config/common/upload');
const router = require('express').Router();
// add
router.post("/", truitsController.addFruits);
//lấy hêt danh sach
router.get("/", truitsController.getFruits);
//lấy 1 sp chi tiết
router.get("/:id", truitsController.getFruit)
// Get danh sách Fruits (danh sách trả về gồm: name, quantity, price, id_ditributor) nằm trong khoảng giá (query giá cao nhất, giá thấp nhất) và sắp xếp theo quantity (giảm dần)
router.get("/getGiaSX", truitsController. getGiaSX);
// Get danh sách Fruits (danh sách trả về gồm: name, quantity, price, id_ditributor) có chữ cái bắt đầu tên là A hoặc X
router.get("/getQueryhai", truitsController.getQueryhai);
//update
router.put("/:id", truitsController.update);
//ảnh
router.post('/add-fruit-with-file-image', upload.array('image', 5), (req, res) => truitsController.addFruitWithFileImage(req, res));
module.exports = router;