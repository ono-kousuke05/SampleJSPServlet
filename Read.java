import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import bean.EmployeeBean;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class Read extends HttpServlet {
    /** デフォルトシリアルバージョンID.(シリアライズ) */
    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        // 文字コードの指定
        response.setContentType("text/plain; charset=UTF-8");
        // EmployeeBean型の動的配列を作成
        List<EmployeeBean> employeeList = new ArrayList<EmployeeBean>();
        try {
            // "JNDI"という、名前で外部ソースを検索するAPIの、名前検索のスタート地点
            InitialContext ic = new InitialContext();
            // "ds"に JNDI で検索するデータベースの名前を入れる
            DataSource ds = (DataSource) ic.lookup("java:/comp/env/jdbc");
            // "con"に"ds"へ接続する処理を格納
            Connection con = ds.getConnection();
            // "sql"にSQL文を格納
            String sql = "SELECT * FROM Employees";
            // "con" で "ds" に接続して、SQL文の型を作っておく
            PreparedStatement st = con.prepareStatement(sql);
            //"rs"にSQL文の結果を格納
            ResultSet rs = st.executeQuery();
            //一行づつSQLから
            while (rs.next()) {
                //空のインスタンス作成
                EmployeeBean employeeBean = new EmployeeBean();
                //SQLで取得した情報をセットしていく
                employeeBean.setEmployeeId(rs.getInt("employeeId"));
                employeeBean.setEmployeeName(rs.getString("employeeName"));
                employeeBean.setHireFiscalYear(rs.getInt("hireFiscalYear"));
                employeeBean.setEmail(rs.getString("email"));
                //動的配列に追加
                employeeList.add(employeeBean);
            }
            // データベースの切断処理
            st.close();
            con.close();
        } catch (Exception e) {
            // エラー処理
            e.printStackTrace();
        }
        // requestオブジェクトに、employeeList動的配列を"empList"をキーとして保存(リクエストスコープ)
        request.setAttribute("empList", employeeList);
        // リクエストごとに通信が切断されるから、リクエストをまたいでデータを保持するためにさっきの動的配列を"sessionに保存"
        HttpSession session = request.getSession();
        //"sessionに保存"
        session.setAttribute("readList", employeeList);
        // 指定した"read.jsp"で配列を使えるように
        RequestDispatcher rd = request.getRequestDispatcher("read.jsp");
        // "read.jsp" にデータを渡した
        rd.forward(request, response);
    }
}
