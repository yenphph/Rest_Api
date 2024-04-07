var express = require('express');
var router = express.Router();

const Distributors = require('../models/distributor');
const Fruits = require('../models/fruits');
const Users = require('../models/users');
const Upload = require('../config/common/upload');

/* GET users listing. */
// router.get('/', function(req, res, next) {
//   res.send('respond with a resource');
// });
router.get('/get-list-distributor', async (req, res) => {
  try {
    // lấy danh sách theo thứ tự distributors mới nhất
    const data = await Distributors.find();
    if (data) {
      res.json({
        "status": 200,
        "data": data
      })
    } else {
      res.json({
        "status": 400,
        "data": []
      })
    }
  } catch (error) {
    console.log(error);
  }
});
router.get('/search-distributor', async (req, res) => {
  try {
    const key = req.query.key

    const data = await Distributors.find({ name: { "$regex": key, "$options": "i" } })
      .sort({ createdAt: -1 });

    if (data) {
      res.json({
        "status": 200,
        "messenger": "Thành công",
        "data": data
      })
    } else {
      res.json({
        "status": 400,
        "messenger": "Lỗi, không thành công",
        "data": []
      })
    }
  } catch (error) {
    console.log(error);
  }
});
router.post('/add-distributor', async (req, res) => {
  try {
    const data = req.body;
    const newDistributors = new Distributors({
      name: data.name
    });
    const result = await newDistributors.save();
    if (result) {
      res.json({
        'status': 200,
        "data": result
      })
    } else {
      res.json({
        "status": 400,
        "data": []
      })
    }
  } catch (error) {

  }
});
router.delete('/del-distributor/:id', async (req, res) => {
  try {
    const { id } = req.params
    const result = await Distributors.findByIdAndDelete(id);
    if (result) {
      res.json({
        "status": 200,
        "data": result
      })
    } else {
      res.json({
        "status": 400,
        "data": []
      })
    }
  } catch (error) {
    console.log(error);
  }
})
router.put('/update-distribute/:id', async (req, res) => {
  try {
    const { id } = req.params;
    const data = req.body;
    const result = await Distributors.findByIdAndUpdate(id, { name: data.name })
    if (result) {
      res.json({
        "status": 200,
        "data": result
      })
    } else {
      res.json({
        "status": 400,
        "data": null
      })
    }
  } catch (error) {

  }
})
// ds fruuit
// router.get('/get-list-fruit', async (req, res, next) =>{
//     const au
// })
// đăbng ký
const Transporter = require('../config/common/mail')
router.post('/register-send-email', Upload.single('avartar'), async (req, res) => {
    try {
        const data = req.body;
        const { file } = req
        const newUser = Users({
            username: data.username,
            password: data.password,
            email: data.email,
            name: data.name,
            avartar: `${req.protocol}://${req.get("host")}/uploads/${file.filename}`,
            //url avatar http://localhost:3000/uploads/filename
        })
        const result = await newUser.save()
        if (result) { //Gửi mail
            const mailOptions = {
                from: "thanghtph31577@fpt.edu.vn", //email gửi đi
                to: result.email, // email nhận
                subject: "Đăng ký thành công", //subject
                text: "Cảm ơn bạn đã đăng ký", // nội dung mail
            };
            // Nếu thêm thành công result !null trả về dữ liệu
            await Transporter.sendMail(mailOptions); // gửi mail
            res.json({
                "status": 200,
                "messenger": "Thêm thành công",
                "data": result
            })
        } else {// Nếu thêm không thành công result null, thông báo không thành công
            res.json({
                "status": 400,
                "messenger": "Lỗi, thêm không thành công",
                "data": []
            })
        }
    } catch (error) {
        console.log(error);
    }
})

// Chu Ys
const JWT = require('jsonwebtoken');
const SECRETKEY = "FPTPOLYTECHNIC"
router.post('/login', async (req, res) => {
    try {
        const { username, password } = req.body;
        const user = await Users.findOne({ username, password })
        if (user) {
            //Token người dùng sẽ sử dụng gửi lên trên header mỗi lần muốn gọi api
            const token = JWT.sign({ id: user._id }, SECRETKEY, { expiresIn: '1h' });
            //Khi token hết hạn, người dùng sẽ call 1 api khác để lấy token mới
            //Lúc này người dùng sẽ truyền refreshToken lên để nhận về 1 cặp token,refreshToken mới
            //Nếu cả 2 token đều hết hạn người dùng sẽ phải thoát app và đăng nhập lại
            const refreshToken = JWT.sign({ id: user._id }, SECRETKEY, { expiresIn: '1d' })
            //expiresIn thời gian token
            res.json({
                "status": 200,
                "messenger": "Đăng nhâp thành công",
                "data": user,
                "token": token,
                "refreshToken": refreshToken
            })
        } else {
            // Nếu thêm không thành công result null, thông báo không thành công
            res.json({
                "status": 400,
                "messenger": "Lỗi, đăng nhập không thành công",
                "data": []
            })
        }
    } catch (error) {
        console.log(error);
    }
})
//get ds
router.get('/get-list-fruit', async (req, res, next) => {
  const authHeader = req.header['authorization'];
  console.log(authHeader);
  if (!authHeader) {
    return res.sendStatus(401);//kiem tra xem header Authorization có tồn tại không
  }
  const token = authHeader.split('')[1];
  if (!token) {
    return res.sendStatus(401);// Kiểm tra xem token có tồn tại không
  }
  try {
    const payload = JWT.verify(token, SECRETKEY); //xac thuc token 
    console.log(payload);
    const data = await Fruits.find().populate('id_distributor');
    res.json({
      "status": 200,
      "data": data
    })
  } catch (error) {
    if(error instanceof JWT.TokenExpiredError){
      return res.sendStatus(401);//token het han
    }else{
      console.error(error);
      return res.sendStatus(403);//loi xac thuc 
    }
  }
})
// upload image
router.post('/add-fruit-with-file-image', Upload.array('image', 5), async (req, res) => {
  //Upload.array('image',5) => up nhiều file tối đa là 5
  //upload.single('image') => up load 1 file
  try {
      const data = req.body; // Lấy dữ liệu từ body
      const { files } = req //files nếu upload nhiều, file nếu upload 1 file
      const urlsImage =
          files.map((file) => `${req.protocol}://${req.get("host")}/uploads/${file.filename}`)
      //url hình ảnh sẽ được lưu dưới dạng: http://localhost:3000/upload/filename
      const newfruit = new Fruits({
          name: data.name,
          quantity: data.quantity,
          price: data.price,
          status: data.status,
          image: urlsImage, /* Thêm url hình */
          description: data.description,
          id_distributor: data.id_distributor
      }); //Tạo một đối tượng mới
      const result = (await newfruit.save()).populate("id_distributor"); //Thêm vào database
      if (result) {// Nếu thêm thành công result !null trả về dữ liệu
          res.json({
              "status": 200,
              "messenger": "Thêm thành công",
              "data": result
          })
      } else {// Nếu thêm không thành công result null, thông báo không thành công
          res.json({
              "status": 400,
              "messenger": "Lỗi, thêm không thành công",
              "data": []
          })
      }
  } catch (error) {
      console.log(error);
  }
});

module.exports = router;
