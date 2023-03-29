package ${packageName}.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import ${packageName}.entity.${className};
import ${packageName}.service.${className}Service;
import com.github.pagehelper.PageInfo;
import com.kang.common.dto.AdvancedQueryDto;
import com.kang.common.util.WrapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author K.faWu
 **/
@RestController
@RequestMapping("/${className?uncap_first}")
public class ${className}Controller {
    @Autowired
    private ${className}Service ${className?uncap_first}Service;

/**
* 查询接口，使用POST请求方式，分页通过url进行传值。
* 查询条件以json的形式进行接收，以{@link AdvancedQueryDto}类作为查询条件的模板，
* 其中{@link AdvancedQueryDto#getOperation()}的值是该字段在引入{@link QueryWrapper}时调用的方法
* 可支持的方法名可在{@link WrapperUtil#queryByDto(QueryWrapper, AdvancedQueryDto)}中看到，后续有可能更新。
*
* @param dtoList 查询条件
* @param pageNum 页码
* @param pageSize 页数
* @return 查询结果
*/
@PostMapping("query")
public ResponseEntity<?> query(@RequestParam Integer pageNum, @RequestParam Integer pageSize,
@RequestBody List
<AdvancedQueryDto> dtoList) {
    PageInfo<${className}> pageInfo = ${className?uncap_first}Service.query(dtoList, pageNum, pageSize);
        return ResponseEntity.ok().body(pageInfo);
    }

    /**
    * 保存接口，该接口将新增和修改合二为一，当接收的数据没有id时进行新增，有id时则是修改
    * @param students 进行操作的数据
    * @return 操作结果
    */
    @PutMapping("save")
    public ResponseEntity<?> save(@RequestBody List<${className}> ${className?uncap_first}s) {
        ${className?uncap_first}Service.saveOrUpdateBatch(${className?uncap_first}s);
        return ResponseEntity.ok().build();
    }

    /**
    * 删除接口，通过id对数据进行删除，但需要注意这里是逻辑删除，并不会真的删除数据
    * @param ids id数组
    * @return 操作结果
    */
    @PostMapping("delete")
    public ResponseEntity<?> delete (@RequestBody List<Long> ids) {
        ${className?uncap_first}Service.delete(ids);
        return ResponseEntity.ok().build();
    }
}
