import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
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

public class Update extends HttpServlet {
    /** デフォルトシリアルバージョンID.(シリアライズ) */
    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        // 文字コードの作成
        response.setContentType("text/plain; charset-UTF-8");
        PrintWriter out = response.getWriter();
        // PrintWriterクラスのprintlnメソッドを使用し、html上で文字列を出力する
        out.println("Updateクラスは正常に作動しています");
    }

    public void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        // 文字コードの作成
        response.setContentType("text/plain; charset-UTF-8");

        String mode = request.getParameter("mode");
        if ("doupdate".equals(mode)) {
            update(request, response);
        } else {
            // リクエストデータからemployeeIdを取得
            int employeeId = Integer.parseInt(request.getParameter(
                    "employeeID"));
            // セッションデータからreadページで詰めた社員リストを取得
            HttpSession session = request.getSession();
            List<EmployeeBean> readList = (List<EmployeeBean>) session
                    .getAttribute("readList");
            // 更新画面で表示する社員情報クラスを作成
            EmployeeBean employeeBean = new EmployeeBean();
            // セッションデータの社員リストから、更新画面でボタンが押下された社員データを取得
            for (EmployeeBean items : readList) {
                if (items.getEmployeeId() == employeeId) {
                    employeeBean.setEmployeeId(items.getEmployeeId());
                    employeeBean.setEmployeeName(items.getEmployeeName());
                    employeeBean.setHireFiscalYear(items.getHireFiscalYear());
                    employeeBean.setEmail(items.getEmail());
                }
            }
            // リクエストスコープに、EmployeeBeanを設定して呼び出しキーは"updateKey"
            request.setAttribute("updateKey", employeeBean);
            // 画面遷移処理
            RequestDispatcher rd = request.getRequestDispatcher("update.jsp");
            rd.forward(request, response);
        }
    }

    // PrintWriter out = response.getWriter();
    // // PrintWriterクラスのprintlnメソッドを使用し、html上で文字列を出力する
    // out.println("更新画面作成");

    void update(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        try {
            // "JNDI"という、名前で外部ソースを検索するAPIの、名前検索のスタート地点
            InitialContext ic = new InitialContext();
            // "ds"に JNDI で検索するデータベースの名前を入れる
            DataSource ds = (DataSource) ic.lookup("java:/comp/env/jdbc");
            // "con"に"ds"へ接続する処理を格納
            Connection con = ds.getConnection();
            int employeeId = Integer.parseInt(request.getParameter(
                    "employeeID"));
            String email = request.getParameter("email");
            String sql = "update Employees　" + "set email = ' " + email + "' "
                    + "where employeeID = " + employeeId;
            PreparedStatement st = con.prepareStatement(sql);
            st.executeQuery();
            // データベースの切断処理
            st.close();
            con.close();
        } catch (Exception e) {
            // エラー処理
            e.printStackTrace();
        }
        response.sendRedirect("read");
    }
}
