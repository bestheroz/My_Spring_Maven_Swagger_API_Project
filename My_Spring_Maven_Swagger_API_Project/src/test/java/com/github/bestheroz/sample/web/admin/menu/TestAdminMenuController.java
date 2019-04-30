package com.github.bestheroz.sample.web.admin.menu;

import com.github.bestheroz.sample.web.admin.menu.response.GetSampleMenuMstVOListResponseVO;
import com.github.bestheroz.sample.web.tablevo.samplemenumst.TableSampleMenuMstDAO;
import com.github.bestheroz.sample.web.tablevo.samplemenumst.TableSampleMenuMstVO;
import com.github.bestheroz.standard.common.exception.CommonException;
import com.github.bestheroz.standard.common.util.MyAccessBeanUtils;
import com.github.bestheroz.standard.common.util.MyMapperUtils;
import com.github.bestheroz.standard.common.util.MyTestUtils;
import com.github.bestheroz.standard.common.valuelabel.response.GetValueLabeVOListResponseVO;
import com.github.bestheroz.standard.test.DefaultTestClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Collections;

public class TestAdminMenuController extends DefaultTestClass {
    @Test
    public void 메뉴데이터_취득() throws Exception {
        final MvcResult mvcResult = MyTestUtils.performPost("/sample/admin/menu/getSampleMenuMstVOList").andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8)).andReturn();
        Assertions.assertNotNull(MyMapperUtils.writeObjectAsArrayList(mvcResult.getResponse().getContentAsString(), GetSampleMenuMstVOListResponseVO.class).get(0).getMenuId());
    }

    @Test
    public void 메뉴데이터_추가() throws Exception {
        final TableSampleMenuMstVO vo = new TableSampleMenuMstVO();
        vo.setMenuNm("메뉴데이터_추가");
        vo.setDispSeq(999);
        vo.setMenuTyp("P");
        vo.setPower(999);
        vo.setParMenuId(1000000000);
        vo.setUseYn("Y");
        final MvcResult mvcResult = MyTestUtils.performPost("/sample/admin/menu/insertSampleMenuMst", vo).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8)).andReturn();
        Assertions.assertEquals(MyMapperUtils.writeObjectAsJsonObject(mvcResult.getResponse().getContentAsString()), CommonException.EXCEPTION_SUCCESS_NORMAL.getJsonObject());
        Assertions.assertEquals(MyAccessBeanUtils.getBean(TableSampleMenuMstDAO.class).getSampleMenuMstVO(vo, Collections.singletonList("menuNm")).getMenuNm(), "메뉴데이터_추가");
    }

    @Test
    public void 메뉴데이터_수정() throws Exception {
        final TableSampleMenuMstVO vo = new TableSampleMenuMstVO();
        vo.setMenuId(1000000000);
        vo.setRemark1("메뉴데이터_수정");
        final MvcResult mvcResult = MyTestUtils.performPost("/sample/admin/menu/updateSampleMenuMst", vo).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8)).andReturn();
        Assertions.assertEquals(MyMapperUtils.writeObjectAsJsonObject(mvcResult.getResponse().getContentAsString()), CommonException.EXCEPTION_SUCCESS_NORMAL.getJsonObject());
        Assertions.assertEquals(MyAccessBeanUtils.getBean(TableSampleMenuMstDAO.class).getSampleMenuMstVO(vo, Collections.singletonList("menuId")).getRemark1(), "메뉴데이터_수정");
    }

    @Test
    public void 메뉴데이터_삭제() throws Exception {
        final TableSampleMenuMstVO vo = new TableSampleMenuMstVO();
        vo.setMenuId(1000000000);
        final MvcResult mvcResult = MyTestUtils.performPost("/sample/admin/menu/deleteSampleMenuMst", vo).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8)).andReturn();
        Assertions.assertEquals(MyMapperUtils.writeObjectAsJsonObject(mvcResult.getResponse().getContentAsString()), CommonException.EXCEPTION_SUCCESS_NORMAL.getJsonObject());
        Assertions.assertNull(MyAccessBeanUtils.getBean(TableSampleMenuMstDAO.class).getSampleMenuMstVO(vo, Collections.singletonList("menuId")));
    }

    @Test
    public void 카테고리_select리스트_가져오기() throws Exception {
        final MvcResult mvcResult = MyTestUtils.performPost("/sample/admin/menu/getPMenuValueLableVOList").andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8)).andReturn();
        Assertions.assertTrue(MyMapperUtils.writeObjectAsArrayList(mvcResult.getResponse().getContentAsString(), GetValueLabeVOListResponseVO.class).size() > 0);
    }

    @Test
    public void 엑셀다운로드() throws Exception {
        MyTestUtils.performPost("/sample/admin/menu/adminMenu.xlsx").andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")).andReturn();
        // Assertions.assertTrue(MyMapperUtils.writeObjectAsArrayList(mvcResult.getResponse().getContentAsString(), ValueLabelVO.class).size() > 0);
    }

    @Test
    public void 엑셀대용량다운로드() throws Exception {
        MyTestUtils.performPost("/sample/admin/menu/adminMenuHugeExcel.xlsx").andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")).andReturn();
        // Assertions.assertTrue(MyMapperUtils.writeObjectAsArrayList(mvcResult.getResponse().getContentAsString(), ValueLabelVO.class).size() > 0);
    }

    @Test
    public void PDF다운로드() throws Exception {
        MyTestUtils.performPost("/sample/admin/menu/adminMenu.pdf").andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_PDF)).andReturn();
        // Assertions.assertTrue(MyMapperUtils.writeObjectAsArrayList(mvcResult.getResponse().getContentAsString(), ValueLabelVO.class).size() > 0);
    }

}
